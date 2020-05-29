package cn.zz.dgcc.DGIOT.utils.Html;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class PDFutils {

    // main测试
    public static byte[] getPDFutils(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象

            // 2.建立一个书写器(Writer)与document对象关联
            //File file = new File("src/main/resources/static/pdf/b.pdf");
            //file.createNewFile();
            //字节输出流

            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setPageEvent(new Watermark("DGCC GRANARY"));// 水印
            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

            // 3.打开文档
            document.open();
            document.addTitle("Title@PDF-Java");// 标题
            document.addAuthor("Author@umiz");// 作者
            document.addSubject("Subject@iText pdf sample");// 主题
            document.addKeywords("Keywords@iTextpdf");// 关键字
            document.addCreator("Creator@umiz`s");// 创建者

            // 4.向文档中添加内容
            new PDFutils().generatePDF(document);

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        return  bytes;
    }

    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    // 最大宽度
    private static final int maxWidth = 520;
    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 18, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 生成PDF文件
    public void generatePDF(Document document) throws Exception {

        // 段落
        Paragraph paragraph = new Paragraph("粮情检测报表", titlefont);
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(10f); //设置段落下空白


        // 表格1
        PdfPTable table = createTable(new float[] {120, 120, 120,120});
        table.addCell(createCell("仓库名称", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("1号仓1廒间", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("仓库类型", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("普通平房仓", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("检测时间", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("2019-09-05 14:37:15", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("天气状况", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("实际储量", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("粮食品种", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("混合麦", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("入库时间", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("2019-09-04", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("粮食等级", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("一等", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("粮食年份", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("2019", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("水分", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("13", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("保管员", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("检测批次", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("2019090514-3", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("仓温", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("0", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("气温", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("25.63", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("仓湿", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("0", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("气湿", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("69.76", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("其他", keyfont, Element.ALIGN_CENTER));
        PdfPCell cell = createCell("", keyfont, Element.ALIGN_CENTER);
        //横向合并合并单元格
        cell.setColspan(3);
        table.addCell(cell);
        table.addCell(createCell("检测结果分析以及处理意见", keyfont, Element.ALIGN_CENTER));
        cell = createCell("", keyfont, Element.ALIGN_CENTER);
        //横向合并合并单元格
        cell.setColspan(3);
        table.addCell(cell);

        // 表格2
        PdfPTable table2 = createTable(new float[] { 70, 70, 70, 70, 70, 70, 70, 70});
        for (int i = 0; i <= 5; i++){
            for(int j = 0; j <= 7; j++){
                if(i == 0 && j == 0){
                    table2.addCell(createCell("行/列", keyfont, Element.ALIGN_CENTER));
                }else if (i == 0){
                    table2.addCell(createCell("列"+j, keyfont, Element.ALIGN_CENTER));
                }else if(j == 0){
                    table2.addCell(createCell("行"+i, keyfont, Element.ALIGN_CENTER));
                }else{
                    table2.addCell(createCell("数据"+ i*j, keyfont, Element.ALIGN_CENTER));
                }

            }

        }


        document.add(paragraph);
        document.add(table);
        document.add( Chunk.NEWLINE );
        document.add(table2);

    }


/**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     * @param value
     * @param font
     * @return
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建指定列宽、列数的表格
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建空白的表格
     * @return
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
/**--------------------------创建表格的方法end------------------- ---------*/




    public static void PDFOutputStream( String fileName, HttpServletRequest request, HttpServletResponse response)
    {
        byte[] content = getPDFutils();
        InputStream is = new ByteArrayInputStream(content);

        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".pdf").getBytes(), StandardCharsets.ISO_8859_1));
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch ( IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }



















}






