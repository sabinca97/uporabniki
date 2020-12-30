package si.fri.rso.samples.imagecatalog.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.imagecatalog.lib.UporabnikMetadata;
import si.fri.rso.samples.imagecatalog.models.converters.UporabnikiMetadataConverter;
import si.fri.rso.samples.imagecatalog.models.entities.UporabnikMetadataEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RequestScoped
public class UporabnikMetadataBean {

    private Logger log = Logger.getLogger(UporabnikMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    public List<UporabnikMetadata> getImageMetadata() {

        TypedQuery<UporabnikMetadataEntity> query = em.createNamedQuery(
                "UporabnikMetadataEntity.getAll", UporabnikMetadataEntity.class);

        List<UporabnikMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(UporabnikiMetadataConverter::toDto).collect(Collectors.toList());

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
