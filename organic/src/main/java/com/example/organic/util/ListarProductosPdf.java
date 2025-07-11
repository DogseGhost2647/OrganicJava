package com.example.organic.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.util.Map;

@Component ("productos/list")

public class ListarProductosPdf extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, com.lowagie.text.Document document,
    com.lowagie.text.pdf.PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

    List<Producto> ListadoProductos = (List<Producto>) model.get("productos");

    document.setPageSize(PageSize.LETTER);

    PdfPTable tablaTitulo = new PdfPtable (1);
    PdfPCell Celda = null;
    Celda = new PdfpCell(new Phrase("Lista de productos"));
    celda.setBorder(0);
    celda.setHorizontalAligment(Element.ALIGN_CENTER);

    tablaTitulo.addCell(Celda);
    tablaTitulo.setSpacingAfter(15);

    PdfPTable tablaProductos = new PdfPtable (9);

        ListadoProductos.ForEach(Producto ->{

            tablaProductos.addCell(Producto.getId().toString);
            tablaProductos.addCell(Producto.getNombre());
            tablaProductos.addCell(Producto.getDescripcion());
            tablaProductos.addCell(Producto.getPrecio());
            tablaProductos.addCell(Producto.getCantidadDisponible());
            tablaProductos.addCell(Producto.getDisponible());
            tablaProductos.addCell(Producto.getCategoriaId));
            tablaProductos.addCell(Producto.getcCondicionesCabellosId());
            tablaProductos.addCell(Producto.getTiposCabellosId());

        });

        document.add(tablaTitulo);
        document.add(tablaProductos);

    }
}
