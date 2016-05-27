package pl.com.bottega.photostock.sales.infrastructure.repositories;


/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-27.
 */

public class AbstractRepositoryFactory {

    public static RepositoryFactory getRepositoryByType(RepositoryType typeRepository) {
        switch (typeRepository) {
            case FAKE_REPOSITORY:
                return new FakeRepositoryFactory();
            case SQL_REPOSITORY:
                return new SqlRepositoryFactory();
            case FILE_REPOSITORY:
                return new FileRepositoryFactory();
            default:
                throw new IllegalArgumentException("Bad repository type");
        }
    }
}

class FileRepositoryFactory implements RepositoryFactory {
    @Override
    public Repository getClientRepository() {
        return new FileClientRepository("tmp/clients.csv");
    }

    @Override
    public Repository getProductRepository() {
        return new FileProductRepository("tmp/products.csv");
    }

    @Override
    public Repository getLightBoxRepository() {
        return new FileLightBoxRepository("tmp/light_boxes.csv");
    }

    @Override
    public Repository getPurchaseRepository() {
        return new FileReservationRepository("tmp/reservations.csv");
    }

    @Override
    public Repository getReservationRepository() {
        return new FilePurchaseRepository("tmp/purchases.csv");
    }
}

class FakeRepositoryFactory implements RepositoryFactory {
    @Override
    public Repository getClientRepository() {
        return new ClientRepository();
    }

    @Override
    public Repository getProductRepository() {
        return new ProductRepository();
    }

    @Override
    public Repository getLightBoxRepository() {
        return new LightBoxRepository();
    }

    @Override
    public Repository getReservationRepository() {
        return new ReservationRepository();
    }

    @Override
    public Repository getPurchaseRepository() {
        return new PurchaseRepository();
    }
}

class SqlRepositoryFactory implements RepositoryFactory {
    @Override
    public Repository getClientRepository() {
        return null;
    }

    @Override
    public Repository getProductRepository() {
        return null;
    }

    @Override
    public Repository getLightBoxRepository() {
        return null;
    }

    @Override
    public Repository getPurchaseRepository() {
        return null;
    }

    @Override
    public Repository getReservationRepository() {
        return null;
    }
}
