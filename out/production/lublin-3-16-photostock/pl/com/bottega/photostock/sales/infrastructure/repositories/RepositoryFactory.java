package pl.com.bottega.photostock.sales.infrastructure.repositories;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-27.
 */

public interface RepositoryFactory {

    Repository getClientRepository();

    Repository getProductRepository();

    Repository getLightBoxRepository();

    Repository getReservationRepository();

    Repository getPurchaseRepository();

}
