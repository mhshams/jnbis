package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.record.UserDefinedDescriptiveText;

/**
 * @author ericdsoto
 */
public class UserDefinedTextReader extends RecordReader {

    @Override
    public UserDefinedDescriptiveText read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new IllegalArgumentException("T1::NULL pointer to T2 record");
        }

        UserDefinedDescriptiveText userDefinedText = new UserDefinedDescriptiveText();

        while (true) {
            NistHelper.Tag tag = getTagInfo(token);

            if (tag.type != NistHelper.RT_USER_DEFINED_TEXT) {
                throw new RuntimeException("T2::Invalid Record type = " + tag.type);
            }

            String value = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, true);

            switch(tag.field) {
                case 1:
                    userDefinedText.setLogicalRecordLength(value);
                    break;
                case 2:
                    userDefinedText.setImageDesignationCharacter(value);
                    break;
                case 3:
                    userDefinedText.setField003(value);
                    break;
                case 4:
                    userDefinedText.setField004(value);
                    break;
                case 5:
                    userDefinedText.setField005(value);
                    break;
                case 6:
                    userDefinedText.setField006(value);
                    break;
                case 7:
                    userDefinedText.setField007(value);
                    break;
                case 8:
                    userDefinedText.setField008(value);
                    break;
                case 9:
                    userDefinedText.setField009(value);
                    break;
                case 10:
                    userDefinedText.setField010(value);
                    break;
                case 11:
                    userDefinedText.setField011(value);
                    break;
                case 12:
                    userDefinedText.setField012(value);
                    break;
                case 13:
                    userDefinedText.setField013(value);
                    break;
                case 14:
                    userDefinedText.setField014(value);
                    break;
                case 15:
                    userDefinedText.setField015(value);
                    break;
                case 16:
                    userDefinedText.setField016(value);
                    break;
                case 17:
                    userDefinedText.setField017(value);
                    break;
                case 18:
                    userDefinedText.setField018(value);
                    break;
                case 20:
                    userDefinedText.setField020(value);
                    break;
                case 21:
                    userDefinedText.setField021(value);
                    break;
                case 22:
                    userDefinedText.setField022(value);
                    break;
                case 23:
                    userDefinedText.setField023(value);
                    break;
                case 24:
                    userDefinedText.setField024(value);
                    break;
                case 25:
                    userDefinedText.setField025(value);
                    break;
                case 26:
                    userDefinedText.setField026(value);
                    break;
                case 27:
                    userDefinedText.setField027(value);
                    break;
                case 28:
                    userDefinedText.setField028(value);
                    break;
                case 29:
                    userDefinedText.setField029(value);
                    break;
                case 30:
                    userDefinedText.setField030(value);
                    break;
                case 31:
                    userDefinedText.setField031(value);
                    break;
                case 32:
                    userDefinedText.setField032(value);
                    break;
                case 33:
                    userDefinedText.setField033(value);
                    break;
                case 34:
                    userDefinedText.setField034(value);
                    break;
                case 35:
                    userDefinedText.setField035(value);
                    break;
                case 36:
                    userDefinedText.setField036(value);
                    break;
                case 37:
                    userDefinedText.setField037(value);
                    break;
                case 38:
                    userDefinedText.setField038(value);
                    break;
                case 39:
                    userDefinedText.setField039(value);
                    break;
                case 40:
                    userDefinedText.setField040(value);
                    break;
                case 41:
                    userDefinedText.setField041(value);
                    break;
                case 42:
                    userDefinedText.setField042(value);
                    break;
                case 43:
                    userDefinedText.setField043(value);
                    break;
                case 44:
                    userDefinedText.setField044(value);
                    break;
                case 45:
                    userDefinedText.setField045(value);
                    break;
                case 46:
                    userDefinedText.setField046(value);
                    break;
                case 47:
                    userDefinedText.setField047(value);
                    break;
                case 48:
                    userDefinedText.setField048(value);
                    break;
                case 49:
                    userDefinedText.setField049(value);
                    break;
                case 50:
                    userDefinedText.setField050(value);
                    break;
                case 51:
                    userDefinedText.setField051(value);
                    break;
                case 52:
                    userDefinedText.setField052(value);
                    break;
                case 53:
                    userDefinedText.setField053(value);
                    break;
                case 54:
                    userDefinedText.setField054(value);
                    break;
                case 55:
                    userDefinedText.setField055(value);
                    break;
                case 56:
                    userDefinedText.setField056(value);
                    break;
                case 57:
                    userDefinedText.setField057(value);
                    break;
                case 58:
                    userDefinedText.setField058(value);
                    break;
                case 59:
                    userDefinedText.setField059(value);
                    break;
                case 60:
                    userDefinedText.setField060(value);
                    break;
                case 61:
                    userDefinedText.setField061(value);
                    break;
                case 62:
                    userDefinedText.setField062(value);
                    break;
                case 63:
                    userDefinedText.setField063(value);
                    break;
                case 64:
                    userDefinedText.setField064(value);
                    break;
                case 65:
                    userDefinedText.setField065(value);
                    break;
                case 66:
                    userDefinedText.setField066(value);
                    break;
                case 67:
                    userDefinedText.setField067(value);
                    break;
                case 68:
                    userDefinedText.setField068(value);
                    break;
                case 69:
                    userDefinedText.setField069(value);
                    break;
                case 70:
                    userDefinedText.setField070(value);
                    break;
                case 71:
                    userDefinedText.setField071(value);
                    break;
                case 72:
                    userDefinedText.setField072(value);
                    break;
                case 73:
                    userDefinedText.setField073(value);
                    break;
                case 74:
                    userDefinedText.setField074(value);
                    break;
                case 75:
                    userDefinedText.setField075(value);
                    break;
                case 76:
                    userDefinedText.setField076(value);
                    break;
                case 77:
                    userDefinedText.setField077(value);
                    break;
                case 78:
                    userDefinedText.setField078(value);
                    break;
                case 79:
                    userDefinedText.setField079(value);
                    break;
                case 80:
                    userDefinedText.setField080(value);
                    break;
                case 81:
                    userDefinedText.setField081(value);
                    break;
                case 82:
                    userDefinedText.setField082(value);
                    break;
                case 83:
                    userDefinedText.setField083(value);
                    break;
                case 84:
                    userDefinedText.setField084(value);
                    break;
                case 85:
                    userDefinedText.setField085(value);
                    break;
                case 86:
                    userDefinedText.setField086(value);
                    break;
                case 87:
                    userDefinedText.setField087(value);
                    break;
                case 88:
                    userDefinedText.setField088(value);
                    break;
                case 89:
                    userDefinedText.setField089(value);
                    break;
                case 90:
                    userDefinedText.setField090(value);
                    break;
                case 91:
                    userDefinedText.setField091(value);
                    break;
                case 92:
                    userDefinedText.setField092(value);
                    break;
                case 93:
                    userDefinedText.setField093(value);
                    break;
                case 94:
                    userDefinedText.setField094(value);
                    break;
                case 95:
                    userDefinedText.setField095(value);
                    break;
                case 96:
                    userDefinedText.setField096(value);
                    break;
                case 97:
                    userDefinedText.setField097(value);
                    break;
                case 98:
                    userDefinedText.setField098(value);
                    break;
                case 99:
                    userDefinedText.setField099(value);
                    break;
                case 100:
                    userDefinedText.setField100(value);
                    break;
                case 901:
                    userDefinedText.setField901(value);
                    break;
                case 902:
                    userDefinedText.setField902(value);
                    break;
                case 903:
                    userDefinedText.setField903(value);
                    break;
                case 904:
                    userDefinedText.setField904(value);
                    break;
                case 905:
                    userDefinedText.setField905(value);
                    break;
                case 906:
                    userDefinedText.setField906(value);
                    break;
                case 907:
                    userDefinedText.setField907(value);
                    break;
                case 908:
                    userDefinedText.setField908(value);
                    break;
                case 909:
                    userDefinedText.setField909(value);
                    break;
                case 910:
                    userDefinedText.setField910(value);
                    break;
                case 911:
                    userDefinedText.setField911(value);
                    break;
                case 912:
                    userDefinedText.setField912(value);
                    break;
                case 913:
                    userDefinedText.setField913(value);
                    break;
                case 914:
                    userDefinedText.setField914(value);
                    break;
                case 915:
                    userDefinedText.setField915(value);
                    break;
                case 916:
                    userDefinedText.setField916(value);
                    break;
                case 917:
                    userDefinedText.setField917(value);
                    break;
                case 918:
                    userDefinedText.setField918(value);
                    break;
                case 919:
                    userDefinedText.setField919(value);
                    break;
                case 920:
                    userDefinedText.setField920(value);
                    break;
                case 921:
                    userDefinedText.setField921(value);
                    break;
                case 922:
                    userDefinedText.setField922(value);
                    break;
                case 923:
                    userDefinedText.setField923(value);
                    break;
                case 924:
                    userDefinedText.setField924(value);
                    break;
                case 925:
                    userDefinedText.setField925(value);
                    break;
                case 926:
                    userDefinedText.setField926(value);
                    break;
                case 927:
                    userDefinedText.setField927(value);
                    break;
                case 928:
                    userDefinedText.setField928(value);
                    break;
                case 929:
                    userDefinedText.setField929(value);
                    break;
                case 930:
                    userDefinedText.setField930(value);
                    break;
                case 941:
                    userDefinedText.setField941(value);
                    break;
                case 942:
                    userDefinedText.setField942(value);
                    break;
                case 943:
                    userDefinedText.setField943(value);
                    break;
                case 944:
                    userDefinedText.setField944(value);
                    break;
                case 945:
                    userDefinedText.setField945(value);
                    break;
                case 946:
                    userDefinedText.setField946(value);
                    break;
                case 947:
                    userDefinedText.setField947(value);
                    break;
                case 948:
                    userDefinedText.setField948(value);
                    break;
                case 949:
                    userDefinedText.setField949(value);
                    break;
                case 950:
                    userDefinedText.setField950(value);
                    break;
                case 951:
                    userDefinedText.setField951(value);
                    break;
                case 952:
                    userDefinedText.setField952(value);
                    break;
                case 953:
                    userDefinedText.setField953(value);
                    break;
                case 954:
                    userDefinedText.setField954(value);
                    break;
                case 955:
                    userDefinedText.setField955(value);
                    break;
                case 956:
                    userDefinedText.setField956(value);
                    break;
                default:
                    break;
            }

            if (token.buffer[token.pos++] == NistHelper.SEP_FS) {
                break;
            }
        }

        return userDefinedText;
    }

}
