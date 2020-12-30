package si.fri.rso.samples.imagecatalog.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;


import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
@CrossOrigin
public class UporabnikMetadataApplication extends Application {

}
