package com.lenewblack.wholesale;

import com.google.gson.Gson;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.resource.*;

/**
 * Main entry point for the Le New Black Wholesale Java SDK.
 *
 * <p>Create a client via the builder, then access resources through the
 * typed factory methods:
 *
 * <pre>{@code
 * LnbClient client = LnbClient.builder()
 *     .clientId("your-client-id")
 *     .clientSecret("your-client-secret")
 *     .build();
 *
 * // List products
 * ResultSet<Product> products = client.products()
 *     .list(ProductListParams.builder().setPageSize(50).build());
 *
 * // Auto-paginate all products
 * for (Product p : client.products().paginate(ProductListParams.builder().build())) {
 *     System.out.println(p.getName());
 * }
 * }</pre>
 *
 * <p>All resource instances are created eagerly at build time and are thread-safe
 * for concurrent use.
 */
public final class LnbClient {

    private final ProductResource products;
    private final OrderResource orders;
    private final CollectionResource collections;
    private final RetailerResource retailers;
    private final FabricResource fabrics;
    private final SizingResource sizings;
    private final InventoryResource inventory;
    private final PriceResource prices;
    private final SalesDocumentResource salesDocuments;
    private final FileResource files;
    private final SalesCatalogResource salesCatalogs;
    private final InvoiceResource invoices;
    private final SelectionResource selections;

    LnbClient(HttpClient httpClient, TokenManager tokenManager, Gson gson,
              String baseUrl, RequestOptions defaultOptions) {
        this.products = new ProductResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.orders = new OrderResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.collections = new CollectionResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.retailers = new RetailerResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.fabrics = new FabricResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.sizings = new SizingResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.inventory = new InventoryResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.prices = new PriceResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.salesDocuments = new SalesDocumentResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.files = new FileResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.salesCatalogs = new SalesCatalogResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.invoices = new InvoiceResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
        this.selections = new SelectionResource(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public static LnbClientBuilder builder() {
        return new LnbClientBuilder();
    }

    public ProductResource products() { return products; }
    public OrderResource orders() { return orders; }
    public CollectionResource collections() { return collections; }
    public RetailerResource retailers() { return retailers; }
    public FabricResource fabrics() { return fabrics; }
    public SizingResource sizings() { return sizings; }
    public InventoryResource inventory() { return inventory; }
    public PriceResource prices() { return prices; }
    public SalesDocumentResource salesDocuments() { return salesDocuments; }
    public FileResource files() { return files; }
    public SalesCatalogResource salesCatalogs() { return salesCatalogs; }
    public InvoiceResource invoices() { return invoices; }
    public SelectionResource selections() { return selections; }
}
