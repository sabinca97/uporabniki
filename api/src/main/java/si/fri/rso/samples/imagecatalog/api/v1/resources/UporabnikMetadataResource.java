package si.fri.rso.samples.imagecatalog.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import si.fri.rso.samples.imagecatalog.lib.UporabnikMetadata;
import si.fri.rso.samples.imagecatalog.services.beans.UporabnikMetadataBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class UporabnikMetadataResource {

    private Logger log = Logger.getLogger(UporabnikMetadataResource.class.getName());

    @Inject
    private UporabnikMetadataBean uporabnikMetadataBean;

    @Context
    protected UriInfo uriInfo;



    @GET
    @CrossOrigin(allowOrigin="*")
    public Response getUporabnikMetadata() {

        List<UporabnikMetadata> uporabnikMetadata = uporabnikMetadataBean.getUporabnikMetadataFilter(uriInfo);

        //dodam st objav, ki se klice iz mikrostoritve za slike
        uporabnikMetadata.forEach((uporabnik) -> {
            Integer stObjav = uporabnikMetadataBean.getImagesForUser(uporabnik.getId());
            uporabnik.setStObjav(stObjav);
        });
        return Response.status(Response.Status.OK).entity(uporabnikMetadata).build();
    }


    @GET
    @Path("/{id}")
    public Response getUporabnikMetadata(@PathParam("id") Integer id) {

        UporabnikMetadata uporabnikMetadata = uporabnikMetadataBean.getUporabnikiMetadata(id);
        uporabnikMetadataBean.getUporabnikiMetadata(id);

        if (uporabnikMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Integer stObjav = uporabnikMetadataBean.getImagesForUser(id);
        uporabnikMetadata.setStObjav(stObjav);

        return Response.status(Response.Status.OK).entity(uporabnikMetadata).build();
    }


    @POST
    public Response createUporabnikMetadata(UporabnikMetadata uporabnikMetadata) {

        uporabnikMetadata = uporabnikMetadataBean.createUporabnikMetadata(uporabnikMetadata);

        return Response.status(Response.Status.OK).entity(uporabnikMetadata).build();

    }

    @DELETE
    @Path("{uporabnikId}")
    public Response deleteImageMetadata(@Parameter(description = "Uporabnik ID.", required = true)
                                        @PathParam("uporabnikId") Integer uporabnikId) {

        boolean deleted = uporabnikMetadataBean.deleteUporabnikMetadata(uporabnikId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
