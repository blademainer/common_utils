package com.xiongyingqi.utils.code;

import org.junit.Test;

public class CodeBuilderTest {

    @Test
    public void testBuild() throws Exception {
        CodeHelper.newCodeBuilder("D:\\workspace_JavaEE\\IWasHere\\IWasHere_ENTITY\\src\\main\\domainbak")
                .removeAnnotations().build();
    }
}