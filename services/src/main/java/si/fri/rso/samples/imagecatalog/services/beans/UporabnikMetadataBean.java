package si.fri.rso.samples.imagecatalog.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import si.fri.rso.samples.imagecatalog.lib.UporabnikMetadata;
import si.fri.rso.samples.imagecatalog.models.converters.UporabnikiMetadataConverter;
import si.fri.rso.samples.imagecatalog.models.entities.UporabnikMetadataEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RequestScoped
public class UporabnikMetadataBean {

    private Logger log = Logger.getLogger(UporabnikMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    private Client httpClient;
    private String baseUrlImages;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrlImages = "http://localhost:8080"; // only for demonstration
    }

    public List<UporabnikMetadata> getImageMetadata() {

        TypedQuery<UporabnikMetadataEntity> query = em.createNamedQuery(
                "UporabnikMetadataEntity.getAll", UporabnikMetadataEntity.class);

        List<UporabnikMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(UporabnikiMetadataConverter::toDto).collect(Collectors.toList());

    }


    public UporabnikMetadata getUporabnikiMetadata(Integer id) {

        UporabnikMetadataEntity uporabnikMetadataEntity = em.find(UporabnikMetadataEntity.class, id);

        if (uporabnikMetadataEntity == null) {
            throw new NotFoundException();
        }

        UporabnikMetadata uporabnikMetadata = UporabnikiMetadataConverter.toDto(uporabnikMetadataEntity);

        return uporabnikMetadata;
    }

    public List<UporabnikMetadata> getUporabnikMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, UporabnikMetadataEntity.class, queryParameters).stream()
                .map(UporabnikiMetadataConverter::toDto).collect(Collectors.toList());
    }



    public UporabnikMetadata createUporabnikMetadata(UporabnikMetadata uporabnikMetadata) {

        UporabnikMetadataEntity uporabnikMetadataEntity = UporabnikiMetadataConverter.toEntity(uporabnikMetadata);

        try {
            beginTx();
            em.persist(uporabnikMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (uporabnikMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return UporabnikiMetadataConverter.toDto(uporabnikMetadataEntity);
    }



    public Integer getImagesForUser(Integer userId) {

        log.info("Calling comments service: getting comment count.");

        try {
            return httpClient
                    .target(baseUrlImages + "/v1/images/byUserCount/" +String.valueOf(userId) )
                    .request().get(new GenericType<Integer>() {
                    });
        }
        catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }



    public boolean deleteUporabnikMetadata(Integer id) {

        UporabnikMetadataEntity uporabnikMetadata = em.find(UporabnikMetadataEntity.class, id);

        if (uporabnikMetadata != null) {
            try {
                beginTx();
                em.remove(uporabnikMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }


    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
