package com.example.organic.util;

// 1. Importaciones que faltaban y son necesarias para iText/OpenPDF y Java Util
import com.lowagie.text.Document;
import com.lowagie.text.Element; // Necesario para Element.ALIGN_CENTER
import com.lowagie.text.PageSize; // Necesario para PageSize.LETTER
import com.lowagie.text.Phrase; // Necesario para Phrase
import com.lowagie.text.pdf.PdfPCell; // Necesario para PdfPCell
import com.lowagie.text.pdf.PdfPTable; // Necesario para PdfPTable
import com.lowagie.text.pdf.PdfWriter; // Ya estaba
import com.example.organic.Entity.ProductosEntity; // <-- ¡MUY IMPORTANTE! Debe ser tu clase de entidad Producto
import java.util.List; // Necesario para List
import java.util.Map; // Ya estaba
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;


@Component("listaProductosPdfView") // <-- Nombre del componente corregido
public class ListarProductosPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<ProductosEntity> listadoProductos = (List<ProductosEntity>) model.get("productos");

        String categoriaFiltro = (String) model.get("categoriaFiltro");

        // Filtro opcional dentro del PDF (puedes quitarlo si ya está filtrado en controlador)
        List<ProductosEntity> productosFiltrados;
        if (categoriaFiltro == null || categoriaFiltro.equalsIgnoreCase("todos")) {
            productosFiltrados = listadoProductos;
        } else {
            productosFiltrados = listadoProductos.stream()
                .filter(p -> p.getCategoria() != null &&
                            p.getCategoria().getNombre().equalsIgnoreCase(categoriaFiltro))
                .collect(Collectors.toList());
        }

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

        // Aquí agregas encabezados a la tabla (opcional pero recomendable)
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

/** package com.example.organic.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.util.Map;

@Component ("listaProductosPdfView")

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
} */
