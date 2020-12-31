package si.fri.rso.samples.imagecatalog.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
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
@CrossOrigin(allowOrigin = "*")
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





}
