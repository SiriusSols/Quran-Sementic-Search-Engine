package org.qsse.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.simpleds.EntityManager;
import org.simpleds.EntityManagerFactory;
import org.simpleds.metadata.PersistenceMetadataRepository;
import org.simpleds.metadata.PersistenceMetadataRepositoryFactory;

public class SimpleDSModule extends AbstractModule {


  @Override
  protected void configure() {

  }

  @Provides
  public PersistenceMetadataRepository getPersistenceMetadataRepository() {
    PersistenceMetadataRepository repository = PersistenceMetadataRepositoryFactory.getPersistenceMetadataRepository();
    if (repository == null) {
      PersistenceMetadataRepositoryFactory factory = new PersistenceMetadataRepositoryFactory();
      factory.setLocations(new String[] { "classpath*:com/main/server/model/**.class" });
      repository = factory.initialize();
    }
    return repository;
  }

  @Provides
  public EntityManager provideEntityManager() {
    EntityManager entityManager = EntityManagerFactory.getEntityManager();
    if (entityManager == null) {
      EntityManagerFactory factory = new EntityManagerFactory();
      factory.setPersistenceMetadataRepository(getPersistenceMetadataRepository());
      factory.setEnforceSchemaConstraints(true);
      entityManager = factory.initialize();
    }
    return entityManager;
  }

	@Provides
	public DatastoreService provideDatastoreService() {
		return DatastoreServiceFactory.getDatastoreService();
	}
}
