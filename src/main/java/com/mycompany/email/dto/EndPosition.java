package com.mycompany.email.dto;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shambhu.mehta on 3/10/2022
 * Description:
 */
@Data
@Getter @Setter
public class EndPosition implements ILineDrawer {

    @Override
    public void draw(PdfCanvas pdfCanvas, Rectangle rectangle) {

    }

    @Override
    public float getLineWidth() {
        return 0;
    }

    @Override
    public void setLineWidth(float v) {

    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(Color color) {

    }
}
