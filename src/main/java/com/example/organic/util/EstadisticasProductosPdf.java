package com.example.organic.util;

import com.example.organic.Entity.ProductosEntity;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.lowagie.text.Font; 
import com.lowagie.text.Image;

public class EstadisticasProductosPdf {

    private final List<ProductosEntity> productos;

    public EstadisticasProductosPdf(List<ProductosEntity> productos) {
        this.productos = productos;
    }

    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4, 50, 50, 60, 50);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // 🌱 Encabezado “ORGANIC”
        Font headerFont = new Font(Font.HELVETICA, 28, Font.BOLD, new Color(34, 139, 34));
        Paragraph header = new Paragraph("ORGANIC", headerFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        // 📊 Subtítulo
        Font tituloFont = new Font(Font.HELVETICA, 16, Font.BOLD, new Color(60, 60, 60));
        Paragraph titulo = new Paragraph("📈 Estadísticas de Productos", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        // 📂 Agrupar productos por categoría
        Map<String, Long> productosPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría",
                        Collectors.counting()
                ));

        // 🧾 Tabla con diseño
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(70);
        tabla.setSpacingBefore(10);
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.setWidths(new float[]{3f, 2f});

        // Encabezados
        Font headerTableFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
        PdfPCell celdaHeader1 = new PdfPCell(new Phrase("Categoría", headerTableFont));
        PdfPCell celdaHeader2 = new PdfPCell(new Phrase("Cantidad de Productos", headerTableFont));

        celdaHeader1.setBackgroundColor(new Color(34, 139, 34));
        celdaHeader2.setBackgroundColor(new Color(34, 139, 34));
        celdaHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaHeader1.setPadding(8);
        celdaHeader2.setPadding(8);

        tabla.addCell(celdaHeader1);
        tabla.addCell(celdaHeader2);

        // Filas
        Font bodyFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.DARK_GRAY);
        for (Map.Entry<String, Long> entry : productosPorCategoria.entrySet()) {
            PdfPCell categoria = new PdfPCell(new Phrase(entry.getKey(), bodyFont));
            PdfPCell cantidad = new PdfPCell(new Phrase(entry.getValue().toString(), bodyFont));

            categoria.setHorizontalAlignment(Element.ALIGN_CENTER);
            cantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            categoria.setPadding(6);
            cantidad.setPadding(6);

            tabla.addCell(categoria);
            tabla.addCell(cantidad);
        }

        document.add(tabla);
        document.add(new Paragraph(" "));

        // 📊 Gráfica de barras con JFreeChart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Long> entry : productosPorCategoria.entrySet()) {
            dataset.addValue(entry.getValue(), "Productos", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Distribución de Productos por Categoría",
                "Categoría",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(new Color(34, 40, 49));
        chart.getPlot().setBackgroundPaint(new Color(245, 245, 245));

        // 🖼️ Convertir gráfico a imagen e insertar
        BufferedImage chartImage = chart.createBufferedImage(500, 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(chartImage, "png", baos);
        com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(baos.toByteArray());
        img.setAlignment(Image.ALIGN_CENTER);
        img.scaleToFit(450, 250);
        document.add(img);

        document.add(new Paragraph(" "));
        Paragraph footer = new Paragraph("Reporte generado automáticamente por el sistema ORGANIC",
                new Font(Font.HELVETICA, 10, Font.ITALIC, new Color(100, 100, 100)));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
    }
}
