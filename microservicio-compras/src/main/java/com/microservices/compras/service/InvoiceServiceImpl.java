package com.microservices.compras.service;

import com.microservices.compras.client.CustomerClient;
import com.microservices.compras.client.ProductClient;
import com.microservices.compras.entity.Invoice;
import com.microservices.compras.entity.InvoiceItem;
import com.microservices.compras.model.Customer;
import com.microservices.compras.model.Product;
import com.microservices.compras.repository.InvoiceItemsRepository;
import com.microservices.compras.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    // repositories
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;




    // feign clients
    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;




    @Override
    public List<Invoice> findInvoiceAll() {
        return  invoiceRepository.findAll();
    }

    // crear factura con datos del cliente y el producto
    // una factura representa una venta, decremento en el stock
    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);

        // consumo el api /products y actualizo el stock
        invoiceDB.getItems().forEach(invoiceItem -> {
            productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });

        return invoiceDB;
    }


    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    // obtener la factura con datos del cliente y el producto
    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if( invoice != null ) {
            // consumo el api /customers y obtengo el cliente
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);

            // consumo el api /products y obtengo los productos
            List<InvoiceItem> listItem = invoice.getItems().stream()
                    .map(invoiceItem -> {
                        Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                        invoiceItem.setProduct(product);
                        return invoiceItem;
                    }).collect(Collectors.toList());
            invoice.setItems(listItem);
        }
        return invoice;
    }

}
