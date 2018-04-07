package com.hsbank.luxclub.util;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by chen_liuchun on 2016/8/10.
 */
public class ImageUtilTest {

    private ImageUtil imageUtil;

    @Before
    public void setUp() throws Exception {
        imageUtil = new ImageUtil();
    }

    @Test
    public void testGetImageFilePathName() throws Exception {
        String imageFilePathName = ImageUtil.getImageFilePathName();
        System.out.println("imageFilePathName: " + imageFilePathName);

    }

    @Test
    public void testGetNewImageFilePathName() throws Exception {
        String newImageFilePathName = ImageUtil.getNewImageFileName();
        System.out.println("newImageFilePathName: " + newImageFilePathName);

    }
}