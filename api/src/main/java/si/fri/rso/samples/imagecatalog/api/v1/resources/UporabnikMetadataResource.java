package si.fri.rso.samples.imagecatalog.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import si.fri.rso.samples.imagecatalog.lib.UporabnikMetadata;
import si.fri.rso.samples.imagecatalog.services.beans.UporabnikMetadataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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


    @POST
    public Response createUporabnikMetadata(UporabnikMetadata uporabnikMetadata) {

        uporabnikMetadata = uporabnikMetadataBean.createUporabnikMetadata(uporabnikMetadata);

        return Response.status(Response.Status.OK).entity(uporabnikMetadata).build();

    }


}
