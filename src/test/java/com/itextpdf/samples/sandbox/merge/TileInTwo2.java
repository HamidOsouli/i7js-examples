/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
    Authors: iText Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
/**
 * Example written by Bruno Lowagie in answer to a question on StackOverflow:
 * http://stackoverflow.com/questions/27011829/divide-one-page-pdf-file-in-two-pages-pdf-file
 */
package com.itextpdf.samples.sandbox.merge;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

import java.io.File;
import java.io.IOException;

public class TileInTwo2 {
    public static final String DEST = "./target/sandbox/merge/tile_in_two2.pdf";

    public static final String SRC = "./src/test/resources/pdfs/united_states.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        new TileInTwo2().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws IOException {
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC));
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        int numberOfPages = srcDoc.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PageSize mediaBox = getHalfWidthPageSize(srcDoc.getPage(i).getPageSizeWithRotation());
            pdfDoc.setDefaultPageSize(mediaBox);
            PdfFormXObject page = srcDoc.getPage(i).copyAsFormXObject(pdfDoc);

            PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
            canvas.addXObject(page, 0, 0);

            canvas = new PdfCanvas(pdfDoc.addNewPage());
            canvas.addXObject(page, -mediaBox.getWidth(), 0);
        }

        pdfDoc.close();
        srcDoc.close();
    }

    private static PageSize getHalfWidthPageSize(Rectangle pageSize) {
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();
        return new PageSize(width / 2, height);
    }
}
