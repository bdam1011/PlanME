import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'

const htmlToPdf = {
    getPdf(title,id) {
        html2Canvas(document.getElementById(id), {
            allowTaint: true,
        }).then(canvas=>{
            //內容的寬度
            let contentWidth = canvas.width;
            //內容高度
            let contentHeight = canvas.height;
            //一頁pdf顯示html頁面生成的canvas高度,a4紙的尺寸[595.28,841.89];
            let pageHeight = contentWidth / 592.28 * 841.89;
            //未生成pdf的html頁面高度
            let leftHeight = contentHeight;
            //頁面偏移
            let position = 0;
            //a4紙的尺寸[595.28,841.89]，html頁面生成的canvas在pdf中圖片的寬高
            let imgWidth = 595.28;
            let imgHeight = 592.28 / contentWidth * contentHeight;
            //canvas轉圖片資料
            let pageData = canvas.toDataURL('image/jpeg', 1.0);
            //新建JsPDF物件
            let PDF = new JsPDF('', 'pt', 'a4');
            //判斷是否分頁
            if (leftHeight < pageHeight) {
                PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
            } else {
                while (leftHeight > 0) {
                    PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight);
                    leftHeight -= pageHeight;
                    position -= 841.89;
                    if (leftHeight > 0) {
                        PDF.addPage()
                    }
                }
            }
            //儲存檔案
            PDF.save(title + '.pdf')
        })
    }
};

export default htmlToPdf;