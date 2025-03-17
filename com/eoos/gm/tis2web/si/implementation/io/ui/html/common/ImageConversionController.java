/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.sun.media.jai.codec.ByteArraySeekableStream;
/*    */ import com.sun.media.jai.codec.ImageCodec;
/*    */ import com.sun.media.jai.codec.ImageEncodeParam;
/*    */ import com.sun.media.jai.codec.ImageEncoder;
/*    */ import com.sun.media.jai.codec.PNGEncodeParam;
/*    */ import com.sun.media.jai.codec.SeekableStream;
/*    */ import java.awt.image.RenderedImage;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.media.jai.RenderedOp;
/*    */ import javax.media.jai.operator.TIFFDescriptor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageConversionController
/*    */   implements Dispatchable
/*    */ {
/*    */   private ClientContext context;
/*    */   private String identifier;
/* 34 */   private Map store = new HashMap<Object, Object>(10);
/*    */ 
/*    */   
/*    */   public ImageConversionController(ClientContext context) {
/* 38 */     this.context = context;
/* 39 */     this.identifier = context.createID();
/* 40 */     context.registerDispatchable(this);
/*    */   }
/*    */   
/*    */   public static ImageConversionController getInstance(ClientContext context) {
/* 44 */     synchronized (context.getLockObject()) {
/* 45 */       ImageConversionController instance = (ImageConversionController)context.getObject(ImageConversionController.class);
/* 46 */       if (instance == null) {
/* 47 */         instance = new ImageConversionController(context);
/* 48 */         context.storeObject(ImageConversionController.class, instance);
/*    */       } 
/* 50 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 55 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getURL(byte[] data, String identifier) {
/*    */     try {
/* 60 */       RenderedOp renderedOp = TIFFDescriptor.create((SeekableStream)new ByteArraySeekableStream(data), null, null, null);
/* 61 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*    */       
/* 63 */       PNGEncodeParam param = PNGEncodeParam.getDefaultEncodeParam((RenderedImage)renderedOp);
/*    */       
/* 65 */       ImageCodec.getCodec("png");
/* 66 */       ImageEncoder encoder = ImageCodec.createImageEncoder("png", baos, (ImageEncodeParam)param);
/* 67 */       encoder.encode((RenderedImage)renderedOp);
/*    */       
/* 69 */       this.store.put(identifier, new PairImpl("image/png", baos.toByteArray()));
/* 70 */       return this.context.constructDispatchURL(this, "get") + "&id=" + identifier;
/* 71 */     } catch (Exception e) {
/* 72 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public ResultObject get(Map params) {
/* 77 */     String identifier = (String)params.get("id");
/* 78 */     Pair pair = (Pair)this.store.get(identifier);
/* 79 */     return new ResultObject(10, pair);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ImageConversionController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */