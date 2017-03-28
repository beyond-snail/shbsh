package com.landicorp.yinshang.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PAY_INFO_BEAN".
*/
public class PayInfoBeanDao extends AbstractDao<PayInfoBean, Void> {

    public static final String TABLENAME = "PAY_INFO_BEAN";

    /**
     * Properties of entity PayInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ResCode = new Property(0, String.class, "resCode", false, "RES_CODE");
        public final static Property ResDesc = new Property(1, String.class, "resDesc", false, "RES_DESC");
        public final static Property MerchantName = new Property(2, String.class, "merchantName", false, "MERCHANT_NAME");
        public final static Property MerchantNo = new Property(3, String.class, "merchantNo", false, "MERCHANT_NO");
        public final static Property TerminalNo = new Property(4, String.class, "terminalNo", false, "TERMINAL_NO");
        public final static Property OperNo = new Property(5, String.class, "operNo", false, "OPER_NO");
        public final static Property Amt = new Property(6, String.class, "amt", false, "AMT");
        public final static Property BatchNo = new Property(7, String.class, "batchNo", false, "BATCH_NO");
        public final static Property TraceNo = new Property(8, String.class, "traceNo", false, "TRACE_NO");
        public final static Property RefNo = new Property(9, String.class, "refNo", false, "REF_NO");
        public final static Property AuthNo = new Property(10, String.class, "authNo", false, "AUTH_NO");
        public final static Property ExpDate = new Property(11, String.class, "expDate", false, "EXP_DATE");
        public final static Property CardNo = new Property(12, String.class, "cardNo", false, "CARD_NO");
        public final static Property CardIssuerCode = new Property(13, String.class, "cardIssuerCode", false, "CARD_ISSUER_CODE");
        public final static Property CardAcquirerCode = new Property(14, String.class, "cardAcquirerCode", false, "CARD_ACQUIRER_CODE");
        public final static Property CardInputType = new Property(15, String.class, "cardInputType", false, "CARD_INPUT_TYPE");
        public final static Property TransChnName = new Property(16, String.class, "transChnName", false, "TRANS_CHN_NAME");
        public final static Property TransEngName = new Property(17, String.class, "transEngName", false, "TRANS_ENG_NAME");
        public final static Property Date = new Property(18, String.class, "date", false, "DATE");
        public final static Property Time = new Property(19, String.class, "time", false, "TIME");
        public final static Property MemInfo = new Property(20, String.class, "memInfo", false, "MEM_INFO");
        public final static Property IsReprint = new Property(21, String.class, "isReprint", false, "IS_REPRINT");
        public final static Property Vendor = new Property(22, String.class, "vendor", false, "VENDOR");
        public final static Property Model = new Property(23, String.class, "model", false, "MODEL");
        public final static Property Version = new Property(24, String.class, "version", false, "VERSION");
        public final static Property Qrcode = new Property(25, String.class, "qrcode", false, "QRCODE");
        public final static Property ESignJpeg = new Property(26, String.class, "eSignJpeg", false, "E_SIGN_JPEG");
        public final static Property ARQC = new Property(27, String.class, "ARQC", false, "ARQC");
        public final static Property UnprNo = new Property(28, String.class, "UnprNo", false, "UNPR_NO");
        public final static Property ATC = new Property(29, String.class, "ATC", false, "ATC");
        public final static Property TVR = new Property(30, String.class, "TVR", false, "TVR");
        public final static Property TSI = new Property(31, String.class, "TSI", false, "TSI");
        public final static Property AID = new Property(32, String.class, "AID", false, "AID");
        public final static Property AIP = new Property(33, String.class, "AIP", false, "AIP");
        public final static Property APPLAB = new Property(34, String.class, "APPLAB", false, "APPLAB");
        public final static Property APPNAME = new Property(35, String.class, "APPNAME", false, "APPNAME");
        public final static Property CVM = new Property(36, String.class, "CVM", false, "CVM");
        public final static Property TermCap = new Property(37, String.class, "TermCap", false, "TERM_CAP");
        public final static Property IAD = new Property(38, String.class, "IAD", false, "IAD");
        public final static Property CSN = new Property(39, String.class, "CSN", false, "CSN");
        public final static Property CardOrg = new Property(40, String.class, "cardOrg", false, "CARD_ORG");
        public final static Property PayNo = new Property(41, String.class, "payNo", false, "PAY_NO");
        public final static Property TransName = new Property(42, String.class, "transName", false, "TRANS_NAME");
        public final static Property ExtOrderNo = new Property(43, String.class, "extOrderNo", false, "EXT_ORDER_NO");
    }


    public PayInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public PayInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PAY_INFO_BEAN\" (" + //
                "\"RES_CODE\" TEXT," + // 0: resCode
                "\"RES_DESC\" TEXT," + // 1: resDesc
                "\"MERCHANT_NAME\" TEXT," + // 2: merchantName
                "\"MERCHANT_NO\" TEXT," + // 3: merchantNo
                "\"TERMINAL_NO\" TEXT," + // 4: terminalNo
                "\"OPER_NO\" TEXT," + // 5: operNo
                "\"AMT\" TEXT," + // 6: amt
                "\"BATCH_NO\" TEXT," + // 7: batchNo
                "\"TRACE_NO\" TEXT," + // 8: traceNo
                "\"REF_NO\" TEXT," + // 9: refNo
                "\"AUTH_NO\" TEXT," + // 10: authNo
                "\"EXP_DATE\" TEXT," + // 11: expDate
                "\"CARD_NO\" TEXT," + // 12: cardNo
                "\"CARD_ISSUER_CODE\" TEXT," + // 13: cardIssuerCode
                "\"CARD_ACQUIRER_CODE\" TEXT," + // 14: cardAcquirerCode
                "\"CARD_INPUT_TYPE\" TEXT," + // 15: cardInputType
                "\"TRANS_CHN_NAME\" TEXT," + // 16: transChnName
                "\"TRANS_ENG_NAME\" TEXT," + // 17: transEngName
                "\"DATE\" TEXT," + // 18: date
                "\"TIME\" TEXT," + // 19: time
                "\"MEM_INFO\" TEXT," + // 20: memInfo
                "\"IS_REPRINT\" TEXT," + // 21: isReprint
                "\"VENDOR\" TEXT," + // 22: vendor
                "\"MODEL\" TEXT," + // 23: model
                "\"VERSION\" TEXT," + // 24: version
                "\"QRCODE\" TEXT," + // 25: qrcode
                "\"E_SIGN_JPEG\" TEXT," + // 26: eSignJpeg
                "\"ARQC\" TEXT," + // 27: ARQC
                "\"UNPR_NO\" TEXT," + // 28: UnprNo
                "\"ATC\" TEXT," + // 29: ATC
                "\"TVR\" TEXT," + // 30: TVR
                "\"TSI\" TEXT," + // 31: TSI
                "\"AID\" TEXT," + // 32: AID
                "\"AIP\" TEXT," + // 33: AIP
                "\"APPLAB\" TEXT," + // 34: APPLAB
                "\"APPNAME\" TEXT," + // 35: APPNAME
                "\"CVM\" TEXT," + // 36: CVM
                "\"TERM_CAP\" TEXT," + // 37: TermCap
                "\"IAD\" TEXT," + // 38: IAD
                "\"CSN\" TEXT," + // 39: CSN
                "\"CARD_ORG\" TEXT," + // 40: cardOrg
                "\"PAY_NO\" TEXT," + // 41: payNo
                "\"TRANS_NAME\" TEXT," + // 42: transName
                "\"EXT_ORDER_NO\" TEXT);"); // 43: extOrderNo
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PAY_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PayInfoBean entity) {
        stmt.clearBindings();
 
        String resCode = entity.getResCode();
        if (resCode != null) {
            stmt.bindString(1, resCode);
        }
 
        String resDesc = entity.getResDesc();
        if (resDesc != null) {
            stmt.bindString(2, resDesc);
        }
 
        String merchantName = entity.getMerchantName();
        if (merchantName != null) {
            stmt.bindString(3, merchantName);
        }
 
        String merchantNo = entity.getMerchantNo();
        if (merchantNo != null) {
            stmt.bindString(4, merchantNo);
        }
 
        String terminalNo = entity.getTerminalNo();
        if (terminalNo != null) {
            stmt.bindString(5, terminalNo);
        }
 
        String operNo = entity.getOperNo();
        if (operNo != null) {
            stmt.bindString(6, operNo);
        }
 
        String amt = entity.getAmt();
        if (amt != null) {
            stmt.bindString(7, amt);
        }
 
        String batchNo = entity.getBatchNo();
        if (batchNo != null) {
            stmt.bindString(8, batchNo);
        }
 
        String traceNo = entity.getTraceNo();
        if (traceNo != null) {
            stmt.bindString(9, traceNo);
        }
 
        String refNo = entity.getRefNo();
        if (refNo != null) {
            stmt.bindString(10, refNo);
        }
 
        String authNo = entity.getAuthNo();
        if (authNo != null) {
            stmt.bindString(11, authNo);
        }
 
        String expDate = entity.getExpDate();
        if (expDate != null) {
            stmt.bindString(12, expDate);
        }
 
        String cardNo = entity.getCardNo();
        if (cardNo != null) {
            stmt.bindString(13, cardNo);
        }
 
        String cardIssuerCode = entity.getCardIssuerCode();
        if (cardIssuerCode != null) {
            stmt.bindString(14, cardIssuerCode);
        }
 
        String cardAcquirerCode = entity.getCardAcquirerCode();
        if (cardAcquirerCode != null) {
            stmt.bindString(15, cardAcquirerCode);
        }
 
        String cardInputType = entity.getCardInputType();
        if (cardInputType != null) {
            stmt.bindString(16, cardInputType);
        }
 
        String transChnName = entity.getTransChnName();
        if (transChnName != null) {
            stmt.bindString(17, transChnName);
        }
 
        String transEngName = entity.getTransEngName();
        if (transEngName != null) {
            stmt.bindString(18, transEngName);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(19, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(20, time);
        }
 
        String memInfo = entity.getMemInfo();
        if (memInfo != null) {
            stmt.bindString(21, memInfo);
        }
 
        String isReprint = entity.getIsReprint();
        if (isReprint != null) {
            stmt.bindString(22, isReprint);
        }
 
        String vendor = entity.getVendor();
        if (vendor != null) {
            stmt.bindString(23, vendor);
        }
 
        String model = entity.getModel();
        if (model != null) {
            stmt.bindString(24, model);
        }
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(25, version);
        }
 
        String qrcode = entity.getQrcode();
        if (qrcode != null) {
            stmt.bindString(26, qrcode);
        }
 
        String eSignJpeg = entity.getESignJpeg();
        if (eSignJpeg != null) {
            stmt.bindString(27, eSignJpeg);
        }
 
        String ARQC = entity.getARQC();
        if (ARQC != null) {
            stmt.bindString(28, ARQC);
        }
 
        String UnprNo = entity.getUnprNo();
        if (UnprNo != null) {
            stmt.bindString(29, UnprNo);
        }
 
        String ATC = entity.getATC();
        if (ATC != null) {
            stmt.bindString(30, ATC);
        }
 
        String TVR = entity.getTVR();
        if (TVR != null) {
            stmt.bindString(31, TVR);
        }
 
        String TSI = entity.getTSI();
        if (TSI != null) {
            stmt.bindString(32, TSI);
        }
 
        String AID = entity.getAID();
        if (AID != null) {
            stmt.bindString(33, AID);
        }
 
        String AIP = entity.getAIP();
        if (AIP != null) {
            stmt.bindString(34, AIP);
        }
 
        String APPLAB = entity.getAPPLAB();
        if (APPLAB != null) {
            stmt.bindString(35, APPLAB);
        }
 
        String APPNAME = entity.getAPPNAME();
        if (APPNAME != null) {
            stmt.bindString(36, APPNAME);
        }
 
        String CVM = entity.getCVM();
        if (CVM != null) {
            stmt.bindString(37, CVM);
        }
 
        String TermCap = entity.getTermCap();
        if (TermCap != null) {
            stmt.bindString(38, TermCap);
        }
 
        String IAD = entity.getIAD();
        if (IAD != null) {
            stmt.bindString(39, IAD);
        }
 
        String CSN = entity.getCSN();
        if (CSN != null) {
            stmt.bindString(40, CSN);
        }
 
        String cardOrg = entity.getCardOrg();
        if (cardOrg != null) {
            stmt.bindString(41, cardOrg);
        }
 
        String payNo = entity.getPayNo();
        if (payNo != null) {
            stmt.bindString(42, payNo);
        }
 
        String transName = entity.getTransName();
        if (transName != null) {
            stmt.bindString(43, transName);
        }
 
        String extOrderNo = entity.getExtOrderNo();
        if (extOrderNo != null) {
            stmt.bindString(44, extOrderNo);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PayInfoBean entity) {
        stmt.clearBindings();
 
        String resCode = entity.getResCode();
        if (resCode != null) {
            stmt.bindString(1, resCode);
        }
 
        String resDesc = entity.getResDesc();
        if (resDesc != null) {
            stmt.bindString(2, resDesc);
        }
 
        String merchantName = entity.getMerchantName();
        if (merchantName != null) {
            stmt.bindString(3, merchantName);
        }
 
        String merchantNo = entity.getMerchantNo();
        if (merchantNo != null) {
            stmt.bindString(4, merchantNo);
        }
 
        String terminalNo = entity.getTerminalNo();
        if (terminalNo != null) {
            stmt.bindString(5, terminalNo);
        }
 
        String operNo = entity.getOperNo();
        if (operNo != null) {
            stmt.bindString(6, operNo);
        }
 
        String amt = entity.getAmt();
        if (amt != null) {
            stmt.bindString(7, amt);
        }
 
        String batchNo = entity.getBatchNo();
        if (batchNo != null) {
            stmt.bindString(8, batchNo);
        }
 
        String traceNo = entity.getTraceNo();
        if (traceNo != null) {
            stmt.bindString(9, traceNo);
        }
 
        String refNo = entity.getRefNo();
        if (refNo != null) {
            stmt.bindString(10, refNo);
        }
 
        String authNo = entity.getAuthNo();
        if (authNo != null) {
            stmt.bindString(11, authNo);
        }
 
        String expDate = entity.getExpDate();
        if (expDate != null) {
            stmt.bindString(12, expDate);
        }
 
        String cardNo = entity.getCardNo();
        if (cardNo != null) {
            stmt.bindString(13, cardNo);
        }
 
        String cardIssuerCode = entity.getCardIssuerCode();
        if (cardIssuerCode != null) {
            stmt.bindString(14, cardIssuerCode);
        }
 
        String cardAcquirerCode = entity.getCardAcquirerCode();
        if (cardAcquirerCode != null) {
            stmt.bindString(15, cardAcquirerCode);
        }
 
        String cardInputType = entity.getCardInputType();
        if (cardInputType != null) {
            stmt.bindString(16, cardInputType);
        }
 
        String transChnName = entity.getTransChnName();
        if (transChnName != null) {
            stmt.bindString(17, transChnName);
        }
 
        String transEngName = entity.getTransEngName();
        if (transEngName != null) {
            stmt.bindString(18, transEngName);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(19, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(20, time);
        }
 
        String memInfo = entity.getMemInfo();
        if (memInfo != null) {
            stmt.bindString(21, memInfo);
        }
 
        String isReprint = entity.getIsReprint();
        if (isReprint != null) {
            stmt.bindString(22, isReprint);
        }
 
        String vendor = entity.getVendor();
        if (vendor != null) {
            stmt.bindString(23, vendor);
        }
 
        String model = entity.getModel();
        if (model != null) {
            stmt.bindString(24, model);
        }
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(25, version);
        }
 
        String qrcode = entity.getQrcode();
        if (qrcode != null) {
            stmt.bindString(26, qrcode);
        }
 
        String eSignJpeg = entity.getESignJpeg();
        if (eSignJpeg != null) {
            stmt.bindString(27, eSignJpeg);
        }
 
        String ARQC = entity.getARQC();
        if (ARQC != null) {
            stmt.bindString(28, ARQC);
        }
 
        String UnprNo = entity.getUnprNo();
        if (UnprNo != null) {
            stmt.bindString(29, UnprNo);
        }
 
        String ATC = entity.getATC();
        if (ATC != null) {
            stmt.bindString(30, ATC);
        }
 
        String TVR = entity.getTVR();
        if (TVR != null) {
            stmt.bindString(31, TVR);
        }
 
        String TSI = entity.getTSI();
        if (TSI != null) {
            stmt.bindString(32, TSI);
        }
 
        String AID = entity.getAID();
        if (AID != null) {
            stmt.bindString(33, AID);
        }
 
        String AIP = entity.getAIP();
        if (AIP != null) {
            stmt.bindString(34, AIP);
        }
 
        String APPLAB = entity.getAPPLAB();
        if (APPLAB != null) {
            stmt.bindString(35, APPLAB);
        }
 
        String APPNAME = entity.getAPPNAME();
        if (APPNAME != null) {
            stmt.bindString(36, APPNAME);
        }
 
        String CVM = entity.getCVM();
        if (CVM != null) {
            stmt.bindString(37, CVM);
        }
 
        String TermCap = entity.getTermCap();
        if (TermCap != null) {
            stmt.bindString(38, TermCap);
        }
 
        String IAD = entity.getIAD();
        if (IAD != null) {
            stmt.bindString(39, IAD);
        }
 
        String CSN = entity.getCSN();
        if (CSN != null) {
            stmt.bindString(40, CSN);
        }
 
        String cardOrg = entity.getCardOrg();
        if (cardOrg != null) {
            stmt.bindString(41, cardOrg);
        }
 
        String payNo = entity.getPayNo();
        if (payNo != null) {
            stmt.bindString(42, payNo);
        }
 
        String transName = entity.getTransName();
        if (transName != null) {
            stmt.bindString(43, transName);
        }
 
        String extOrderNo = entity.getExtOrderNo();
        if (extOrderNo != null) {
            stmt.bindString(44, extOrderNo);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public PayInfoBean readEntity(Cursor cursor, int offset) {
        PayInfoBean entity = new PayInfoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // resCode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // resDesc
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // merchantName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // merchantNo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // terminalNo
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // operNo
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // amt
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // batchNo
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // traceNo
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // refNo
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // authNo
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // expDate
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // cardNo
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // cardIssuerCode
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // cardAcquirerCode
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // cardInputType
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // transChnName
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // transEngName
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // date
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // time
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // memInfo
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // isReprint
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // vendor
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // model
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // version
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // qrcode
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // eSignJpeg
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // ARQC
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // UnprNo
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // ATC
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // TVR
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // TSI
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // AID
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // AIP
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // APPLAB
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // APPNAME
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // CVM
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // TermCap
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // IAD
            cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39), // CSN
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40), // cardOrg
            cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41), // payNo
            cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42), // transName
            cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43) // extOrderNo
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PayInfoBean entity, int offset) {
        entity.setResCode(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setResDesc(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMerchantName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMerchantNo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTerminalNo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOperNo(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAmt(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setBatchNo(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTraceNo(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setRefNo(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setAuthNo(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setExpDate(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCardNo(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCardIssuerCode(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCardAcquirerCode(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCardInputType(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setTransChnName(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setTransEngName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setDate(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setTime(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setMemInfo(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setIsReprint(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setVendor(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setModel(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setVersion(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setQrcode(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setESignJpeg(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setARQC(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setUnprNo(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setATC(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setTVR(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setTSI(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setAID(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setAIP(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setAPPLAB(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setAPPNAME(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setCVM(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setTermCap(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setIAD(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setCSN(cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39));
        entity.setCardOrg(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
        entity.setPayNo(cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41));
        entity.setTransName(cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42));
        entity.setExtOrderNo(cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(PayInfoBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(PayInfoBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(PayInfoBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}