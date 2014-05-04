/**
 * RichTextTest
 */
package com.xiongyingqi.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-8-29 下午4:03:40
 */
public class ClipBoardHelper {

    public static Map<Object, ClipBoardType> getClipBoardTypeMap() {
        Map<Object, ClipBoardType> map = new LinkedHashMap<Object, ClipBoardType>();
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        DataFlavor[] dataFlavors = t.getTransferDataFlavors();
        for (int i = 0; i < dataFlavors.length; i++) {
            DataFlavor dataFlavor = dataFlavors[i];
            ClipBoardType clipBoardType = parseType(dataFlavor);
            try {
                Object object = t.getTransferData(dataFlavor);
                if (dataFlavor.isMimeTypeEqual("text/html")
                        && dataFlavor.isRepresentationClassCharBuffer()) {
                    System.out.println(dataFlavor);
                    System.out.println("object.toString() =========== " + object.toString());
                }
                if (clipBoardType != null) {
                    map.put(object, clipBoardType);
                }
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // if(t.isDataFlavorSupported(DataFlavor.imageFlavor)){
        // try {
        // BufferedImage image = (BufferedImage)
        // t.getTransferData(DataFlavor.imageFlavor);
        // System.out.println(image.toString());
        // } catch (UnsupportedFlavorException e) {
        // e.printStackTrace();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        return map;
    }

    private static ClipBoardType parseType(DataFlavor dataFlavor) {
        //		EntityHelper.print(dataFlavor);
        if (dataFlavor == null) {
            return null;
        }
        /**
         *
         System.out.println("dataFlavor.isFlavorJavaFileListType() ======== "
         * + dataFlavor.isFlavorJavaFileListType()); System.out.println(
         * "dataFlavor.isFlavorRemoteObjectType() ======== " +
         * dataFlavor.isFlavorRemoteObjectType()); System.out.println(
         * "dataFlavor.isFlavorSerializedObjectType() ======== " +
         * dataFlavor.isFlavorSerializedObjectType());
         * System.out.println("dataFlavor.isFlavorTextType() ======== " +
         * dataFlavor.isFlavorTextType());
         * System.out.println("dataFlavor.isMimeTypeEqual() ======== " +
         * dataFlavor.isMimeTypeEqual("text/html")); System.out.println(
         * "dataFlavor.isMimeTypeSerializedObject() ======== " +
         * dataFlavor.isMimeTypeSerializedObject()); System.out.println(
         * "dataFlavor.isRepresentationClassByteBuffer() ======== " +
         * dataFlavor.isRepresentationClassByteBuffer()); System.out.println(
         * "dataFlavor.isRepresentationClassByteBuffer() ======== " +
         * dataFlavor.isRepresentationClassCharBuffer()); System.out.println(
         * "dataFlavor.isRepresentationClassInputStream() ======== " +
         * dataFlavor.isRepresentationClassInputStream()); System.out.println(
         * "dataFlavor.isRepresentationClassReader() ======== " +
         * dataFlavor.isRepresentationClassReader()); System.out.println(
         * "dataFlavor.isRepresentationClassRemote() ======== " +
         * dataFlavor.isRepresentationClassRemote()); System.out.println(
         * "dataFlavor.isRepresentationClassSerializable() ======== " +
         * dataFlavor.isRepresentationClassSerializable());
         */

        ClipBoardType type = null;// 默认转换为字符串类型
        if (dataFlavor.equals(DataFlavor.imageFlavor)) {
            type = ClipBoardType.IMAGE;
        } else if (dataFlavor.isMimeTypeEqual("text/html")
                && dataFlavor.isRepresentationClassCharBuffer()) {
            type = ClipBoardType.HTML;
        } else if (dataFlavor.isMimeTypeEqual("application/rtf")
                && dataFlavor.isRepresentationClassInputStream()) {
            type = ClipBoardType.HTML;
        } else if (dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
            type = ClipBoardType.FILE;
        } else if (dataFlavor.equals(DataFlavor.plainTextFlavor)) {
            type = ClipBoardType.STRINGREADER;
        } else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            type = ClipBoardType.STRING;
        } else if (dataFlavor.isRepresentationClassInputStream()) {
            type = ClipBoardType.INPUT_STREAM;
        }
        return type;
    }

    /**
     * 剪切板内容类型枚举
     *
     * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
     * @version 2013-8-29 下午4:55:59
     */
    public enum ClipBoardType {
        /**
         * 文件类型
         */
        FILE,
        /**
         * 图片类型
         */
        IMAGE,
        /**
         * 字符串类型
         */
        STRING,
        /**
         * 字符流
         */
        STRINGREADER,
        /**
         * IO流
         */
        INPUT_STREAM,
        /**
         * HTML
         */
        HTML
    }

    public static JLabel[] buildClipBoard() {
        Map<Object, ClipBoardType> map = getClipBoardTypeMap();
        Set<Entry<Object, ClipBoardType>> entries = map.entrySet();
        Collection<JLabel> labels = new ArrayList<JLabel>();
        int i = 0;
        for (Iterator iterator = entries.iterator(); iterator.hasNext(); ) {
            Entry<Object, ClipBoardType> entry = (Entry<Object, ClipBoardType>) iterator.next();
            Object object = entry.getKey();
            ClipBoardType clipBoardType = entry.getValue();
            JLabel label = null;
            switch (clipBoardType) {
                case IMAGE:
                    final Image image = (Image) object;
                    label = new JLabel() {
                        {
                            this.setSize(image.getWidth(null), image.getHeight(null));
                        }

                        /**
                         * <br>
                         * 2013-8-29 下午5:18:22
                         *
                         * @see javax.swing.JComponent#paint(java.awt.Graphics)
                         */
                        @Override
                        public void paint(Graphics g) {
                            g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
                        }
                    };
                    break;
                case FILE:

                    break;
                case STRING:

                    break;
                default:
                    break;
            }
            if (label != null) {
                labels.add(label);
            }
            i++;
        }
        return labels.toArray(new JLabel[]{});
    }

    public static void main(String[] args) {
        Map<Object, ClipBoardType> map = getClipBoardTypeMap();
        Set<Entry<Object, ClipBoardType>> entries = map.entrySet();
        for (Iterator iterator = entries.iterator(); iterator.hasNext(); ) {
            Entry<Object, ClipBoardType> entry = (Entry<Object, ClipBoardType>) iterator.next();
            //			EntityHelper.printDetail(entry.getKey());
            //			EntityHelper.printDetail(entry.getValue());
        }
    }
}
