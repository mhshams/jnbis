package org.jnbis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 */

@RunWith(Parameterized.class)
public class AnsiReferencesTest {
    private static final String FILE_PATH = "ansi/references/type-%s.an2";

    private static final String[] FILES = {
            "3",
            "4-14-slaps",
            "4-slaps",
            "4-tpcard",
            "5",
            "6",
            "7-latent",
            "8-sig",
            "8-sig-fax",
            "8-sig-raw",
//            "9-4-iafis",
//            "9-10-14",
            "9-13-9-14-m1",
            "9-13-m1",
            "9-13-std",
            "9-14-m1",
            "9-14-std",
            "10-14-17-piv-index-iris",
            "10-branded-tattoo-mark",
            "10-sap10",
            "10-scar-face-sap50",
            "10-tattoo-face-sap20",
            "10-tattoo-zoom",
            "13-14-latent-match",
            "13-tip-eji-j2l",
            "13-tip-eji-wsq",
            "14-amp-nqm-utf8",
            "14-tip-eji-j2l",
            "14-tip-eji-wsq",
            "14-tpcard-nqm",
            "15-palms",
            "17-iris"
    };

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        List<Object[]> list = new ArrayList<Object[]>(FILES.length);
        for (String file : FILES) {
            list.add(new Object[]{String.format(FILE_PATH, file)});
        }
        return list;
    }

    private NistDecoder nistDecoder;
    private String file;

    public AnsiReferencesTest(String file) {
        this.file = file;
    }

    @Before
    public void setUp() throws Exception {
        nistDecoder = new NistDecoder();
    }

    @Test
    public void references() throws IOException {
        DecodedData decoded = nistDecoder.decode(readFile(file), DecodedData.Format.PNG);
        Assert.assertNotNull(decoded);
    }

    private InputStream readFile(String name) {
        return SampleTest.class.getClassLoader().getResourceAsStream(name);
    }
}
