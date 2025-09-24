package com.example.organic.util;

import com.lowagie.text.Document;
import com.lowagie.text.Element; 
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase; 
import com.lowagie.text.pdf.PdfPCell; 
import com.lowagie.text.pdf.PdfPTable; 
import com.lowagie.text.pdf.PdfWriter; 
import com.example.organic.Entity.ProductosEntity; 
import java.util.List;
import java.util.Map; 
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;


@Component("listaProductosPdfView")
public class ListarProductosPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<ProductosEntity> productosFiltrados = (List<ProductosEntity>) model.get("productos");

        String categoriaFiltro = (String) model.get("categoriaFiltro");

        document.setPageSize(PageSize.LETTER);

        String tituloReporte = (categoriaFiltro == null || categoriaFiltro.equalsIgnoreCase("todos")) ?
                "Lista de productos - Todas las categorías" :
                "Lista de productos - Categoría: " + categoriaFiltro;

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = new PdfPCell(new Phrase(tituloReporte));
        celda.setBorder(0);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(15);

        PdfPTable tablaProductos = new PdfPTable(9);
        tablaProductos.setWidthPercentage(100);

        tablaProductos.addCell("ID");
        tablaProductos.addCell("Nombre");
        tablaProductos.addCell("Descripción");
        tablaProductos.addCell("Precio");
        tablaProductos.addCell("Cantidad Disponible");
        tablaProductos.addCell("Disponible");
        tablaProductos.addCell("Categoría ID");
        tablaProductos.addCell("Condición Cabello ID");
        tablaProductos.addCell("Tipo Cabello ID");

        productosFiltrados.forEach(producto -> {
            tablaProductos.addCell(producto.getId() != null ? producto.getId().toString() : "");
            tablaProductos.addCell(producto.getNombre() != null ? producto.getNombre() : "");
            tablaProductos.addCell(producto.getDescripcion() != null ? producto.getDescripcion() : "");
            tablaProductos.addCell(producto.getPrecio() != null ? producto.getPrecio().toString() : "");
            tablaProductos.addCell(producto.getCantidadDisponible() != null ? producto.getCantidadDisponible().toString() : "");
            tablaProductos.addCell(producto.getDisponible() != null ? producto.getDisponible().toString() : "");
            tablaProductos.addCell(producto.getCategoria() != null && producto.getCategoria().getId() != null ? producto.getCategoria().getId().toString() : "");
            tablaProductos.addCell(producto.getCondicionesCabellos() != null && producto.getCondicionesCabellos().getId() != null ? producto.getCondicionesCabellos().getId().toString() : "");
            tablaProductos.addCell(producto.getTiposCabellos() != null && producto.getTiposCabellos().getId() != null ? producto.getTiposCabellos().getId().toString() : "");
        });

        document.add(tablaTitulo);
        document.add(tablaProductos);
    }

}
