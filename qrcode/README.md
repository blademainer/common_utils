qrcode
============
二维码生成工具：

```java
String logoPath = getClass().getClassLoader().getResource("logo.jpg").getFile();
String folder = new File(logoPath).getParent();

File png = QRCodeGenerator.newGenerator()
        .content("http://xiongyingqi.com")
        .characterSet("UTF-8")
        .errorCorrectionLevel(ErrorCorrectionLevel.H)
        .logoFlag(true)
        .format("png")
        .margin(0)
        .width(300)
        .height(300)
        .path(folder)
        .logoPath(logoPath)
        .generate();
Assert.assertTrue(png.exists());

File png2 = QRCodeGenerator.newGenerator()
        .content("http://xiongyingqi.com")
        .generate();
System.out.println(png);
System.out.println(png2);
Assert.assertTrue(png2.exists());

QRCode qrCode = new QRCode();
String decode = qrCode.decode(png.getPath());
Assert.assertEquals(decode, "http://xiongyingqi.com");
```