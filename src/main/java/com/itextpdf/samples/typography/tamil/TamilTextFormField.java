/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
    Authors: iText Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */

package com.itextpdf.samples.typography.tamil;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.licensekey.LicenseKey;
import com.itextpdf.test.annotations.type.SampleTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(SampleTest.class)
public class TamilTextFormField {

    public static final String DEST = "./target/samples/typography/TamilTextFormField.pdf";
    public static final String FONTS_FOLDER = "./src/main/java/com/itextpdf/samples/typography/tamil/resources/";

    public static void main(String[] args) throws Exception {

        // Load the license file to use typography features
        LicenseKey.loadLicenseFile(System.getenv("ITEXT7_LICENSEKEY") + "/itextkey-typography.xml");

        File file = new File(DEST);
        file.getParentFile().mkdirs();

        new TamilTextFormField().createPDF(DEST);
    }

    public void createPDF(String dest) throws IOException {

        // Create a pdf document along with a Document (default root layout element) instance
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest));
        Document document = new Document(pdfDocument);

        // தமிழ்
        String fieldValue = "\u0BA4\u0BAE\u0BBF\u0BB4\u0BCD";

        String fieldName = "Field name";

        PdfAcroForm form = PdfAcroForm.getAcroForm(document.getPdfDocument(), true);

        PdfFont font = PdfFontFactory.createFont(FONTS_FOLDER + "NotoSansTamil-Regular.ttf",
                PdfEncodings.IDENTITY_H);

        // Embed entire font without any subsetting. Please note that without subset it's impossible to edit a form field
        // with the predefined font
        font.setSubset(false);

        // Create a form field and set some of the properties
        PdfFormField formField = PdfTextFormField.createText(document.getPdfDocument(),
                new Rectangle(50, 750, 100, 25));
        formField
                .setValue(fieldValue)
                .setBorderWidth(2)
                .setFont(font)
                .setFontSize(10)
                .setJustification(1)
                .setFieldName(fieldName);

        form.addField(formField);

        document.close();
    }
}



