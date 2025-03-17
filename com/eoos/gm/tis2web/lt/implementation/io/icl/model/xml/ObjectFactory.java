/*      */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.CheckImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.CommentImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ConfirmationImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.CustomerImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.DateImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.DefectsImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.DescriptionImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.DrvTypeImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.InspTypeImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.KmImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.MechanicImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.NameImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.NumberplateImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.OrdernoImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.PhoneImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.PosFootnoteImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ServTypeImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.StateImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.TextImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.TitleImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ValueImpl;
/*      */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.WorkshopImpl;
/*      */ import java.util.HashMap;
/*      */ import javax.xml.bind.JAXBException;
/*      */ 
/*      */ public class ObjectFactory extends DefaultJAXBContextImpl {
/*   28 */   private static HashMap defaultImplementations = new HashMap<Object, Object>();
/*      */   
/*      */   static {
/*   31 */     defaultImplementations.put(Date.class, DateImpl.class);
/*   32 */     defaultImplementations.put(Mechanic.class, MechanicImpl.class);
/*   33 */     defaultImplementations.put(Footnote.class, FootnoteImpl.class);
/*   34 */     defaultImplementations.put(Title.class, TitleImpl.class);
/*   35 */     defaultImplementations.put(ServiceplanType.class, ServiceplanTypeImpl.class);
/*   36 */     defaultImplementations.put(Bullet.class, BulletImpl.class);
/*   37 */     defaultImplementations.put(Customer.class, CustomerImpl.class);
/*   38 */     defaultImplementations.put(Defects.class, DefectsImpl.class);
/*   39 */     defaultImplementations.put(Serviceplan.class, ServiceplanImpl.class);
/*   40 */     defaultImplementations.put(Numberplate.class, NumberplateImpl.class);
/*   41 */     defaultImplementations.put(Footer.class, FooterImpl.class);
/*   42 */     defaultImplementations.put(Km.class, KmImpl.class);
/*   43 */     defaultImplementations.put(State.class, StateImpl.class);
/*   44 */     defaultImplementations.put(PosFootnote.class, PosFootnoteImpl.class);
/*   45 */     defaultImplementations.put(Sign.class, SignImpl.class);
/*   46 */     defaultImplementations.put(FooterType.class, FooterTypeImpl.class);
/*   47 */     defaultImplementations.put(ParameterType.class, ParameterTypeImpl.class);
/*   48 */     defaultImplementations.put(StdFootnote.class, StdFootnoteImpl.class);
/*   49 */     defaultImplementations.put(Release.class, ReleaseImpl.class);
/*   50 */     defaultImplementations.put(TransmissionType.class, TransmissionTypeImpl.class);
/*   51 */     defaultImplementations.put(HeaderType.class, HeaderTypeImpl.class);
/*   52 */     defaultImplementations.put(Confirmation.class, ConfirmationImpl.class);
/*   53 */     defaultImplementations.put(CplusType.class, CplusTypeImpl.class);
/*   54 */     defaultImplementations.put(Header.class, HeaderImpl.class);
/*   55 */     defaultImplementations.put(Check.class, CheckImpl.class);
/*   56 */     defaultImplementations.put(Text.class, TextImpl.class);
/*   57 */     defaultImplementations.put(EngineType.class, EngineTypeImpl.class);
/*   58 */     defaultImplementations.put(Orderno.class, OrdernoImpl.class);
/*   59 */     defaultImplementations.put(Description.class, DescriptionImpl.class);
/*   60 */     defaultImplementations.put(Name.class, NameImpl.class);
/*   61 */     defaultImplementations.put(Model.class, ModelImpl.class);
/*   62 */     defaultImplementations.put(Engine.class, EngineImpl.class);
/*   63 */     defaultImplementations.put(Position.class, PositionImpl.class);
/*   64 */     defaultImplementations.put(FootnoteType.class, FootnoteTypeImpl.class);
/*   65 */     defaultImplementations.put(StdFootnoteType.class, StdFootnoteTypeImpl.class);
/*   66 */     defaultImplementations.put(PositionsType.class, PositionsTypeImpl.class);
/*   67 */     defaultImplementations.put(Transmission.class, TransmissionImpl.class);
/*   68 */     defaultImplementations.put(Value.class, ValueImpl.class);
/*   69 */     defaultImplementations.put(Workshop.class, WorkshopImpl.class);
/*   70 */     defaultImplementations.put(Comment.class, CommentImpl.class);
/*   71 */     defaultImplementations.put(Cplus.class, CplusImpl.class);
/*   72 */     defaultImplementations.put(ModelType.class, ModelTypeImpl.class);
/*   73 */     defaultImplementations.put(Positions.class, PositionsImpl.class);
/*   74 */     defaultImplementations.put(ServType.class, ServTypeImpl.class);
/*   75 */     defaultImplementations.put(VINType.class, VINTypeImpl.class);
/*   76 */     defaultImplementations.put(BulletType.class, BulletTypeImpl.class);
/*   77 */     defaultImplementations.put(ReleaseType.class, ReleaseTypeImpl.class);
/*   78 */     defaultImplementations.put(DrvType.class, DrvTypeImpl.class);
/*   79 */     defaultImplementations.put(InspType.class, InspTypeImpl.class);
/*   80 */     defaultImplementations.put(Make.class, MakeImpl.class);
/*   81 */     defaultImplementations.put(VIN.class, VINImpl.class);
/*   82 */     defaultImplementations.put(PositionType.class, PositionTypeImpl.class);
/*   83 */     defaultImplementations.put(Parameter.class, ParameterImpl.class);
/*   84 */     defaultImplementations.put(Phone.class, PhoneImpl.class);
/*   85 */     defaultImplementations.put(Label.class, LabelImpl.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectFactory() {
/*   94 */     super(new GrammarInfoImpl(null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object newInstance(Class javaContentInterface) throws JAXBException {
/*  107 */     return super.newInstance(javaContentInterface);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) throws PropertyException {
/*  122 */     return super.getProperty(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String name, Object value) throws PropertyException {
/*  138 */     super.setProperty(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date createDate() throws JAXBException {
/*  148 */     return (Date)newInstance(Date.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date createDate(String value) throws JAXBException {
/*  158 */     return (Date)new DateImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Mechanic createMechanic() throws JAXBException {
/*  168 */     return (Mechanic)newInstance(Mechanic.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Mechanic createMechanic(String value) throws JAXBException {
/*  178 */     return (Mechanic)new MechanicImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Footnote createFootnote() throws JAXBException {
/*  188 */     return (Footnote)newInstance(Footnote.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Title createTitle() throws JAXBException {
/*  198 */     return (Title)newInstance(Title.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Title createTitle(String value) throws JAXBException {
/*  208 */     return (Title)new TitleImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServiceplanType createServiceplanType() throws JAXBException {
/*  218 */     return (ServiceplanType)newInstance(ServiceplanType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Bullet createBullet() throws JAXBException {
/*  228 */     return (Bullet)newInstance(Bullet.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Customer createCustomer() throws JAXBException {
/*  238 */     return (Customer)newInstance(Customer.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Customer createCustomer(String value) throws JAXBException {
/*  248 */     return (Customer)new CustomerImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Defects createDefects() throws JAXBException {
/*  258 */     return (Defects)newInstance(Defects.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Defects createDefects(String value) throws JAXBException {
/*  268 */     return (Defects)new DefectsImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Serviceplan createServiceplan() throws JAXBException {
/*  278 */     return (Serviceplan)newInstance(Serviceplan.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Numberplate createNumberplate() throws JAXBException {
/*  288 */     return (Numberplate)newInstance(Numberplate.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Numberplate createNumberplate(String value) throws JAXBException {
/*  298 */     return (Numberplate)new NumberplateImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Footer createFooter() throws JAXBException {
/*  308 */     return (Footer)newInstance(Footer.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Km createKm() throws JAXBException {
/*  318 */     return (Km)newInstance(Km.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Km createKm(String value) throws JAXBException {
/*  328 */     return (Km)new KmImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public State createState() throws JAXBException {
/*  338 */     return (State)newInstance(State.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public State createState(String value) throws JAXBException {
/*  348 */     return (State)new StateImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PosFootnote createPosFootnote() throws JAXBException {
/*  358 */     return (PosFootnote)newInstance(PosFootnote.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PosFootnote createPosFootnote(String value) throws JAXBException {
/*  368 */     return (PosFootnote)new PosFootnoteImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Sign createSign() throws JAXBException {
/*  378 */     return (Sign)newInstance(Sign.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Sign createSign(String value) throws JAXBException {
/*  388 */     return (Sign)new SignImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FooterType createFooterType() throws JAXBException {
/*  398 */     return (FooterType)newInstance(FooterType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ParameterType createParameterType() throws JAXBException {
/*  408 */     return (ParameterType)newInstance(ParameterType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StdFootnote createStdFootnote() throws JAXBException {
/*  418 */     return (StdFootnote)newInstance(StdFootnote.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Release createRelease() throws JAXBException {
/*  428 */     return (Release)newInstance(Release.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransmissionType createTransmissionType() throws JAXBException {
/*  438 */     return (TransmissionType)newInstance(TransmissionType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HeaderType createHeaderType() throws JAXBException {
/*  448 */     return (HeaderType)newInstance(HeaderType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Confirmation createConfirmation() throws JAXBException {
/*  458 */     return (Confirmation)newInstance(Confirmation.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Confirmation createConfirmation(String value) throws JAXBException {
/*  468 */     return (Confirmation)new ConfirmationImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CplusType createCplusType() throws JAXBException {
/*  478 */     return (CplusType)newInstance(CplusType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Header createHeader() throws JAXBException {
/*  488 */     return (Header)newInstance(Header.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Check createCheck() throws JAXBException {
/*  498 */     return (Check)newInstance(Check.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Check createCheck(String value) throws JAXBException {
/*  508 */     return (Check)new CheckImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Text createText() throws JAXBException {
/*  518 */     return (Text)newInstance(Text.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Text createText(String value) throws JAXBException {
/*  528 */     return (Text)new TextImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EngineType createEngineType() throws JAXBException {
/*  538 */     return (EngineType)newInstance(EngineType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Orderno createOrderno() throws JAXBException {
/*  548 */     return (Orderno)newInstance(Orderno.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Orderno createOrderno(String value) throws JAXBException {
/*  558 */     return (Orderno)new OrdernoImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Description createDescription() throws JAXBException {
/*  568 */     return (Description)newInstance(Description.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Description createDescription(String value) throws JAXBException {
/*  578 */     return (Description)new DescriptionImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Name createName() throws JAXBException {
/*  588 */     return (Name)newInstance(Name.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Name createName(String value) throws JAXBException {
/*  598 */     return (Name)new NameImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Model createModel() throws JAXBException {
/*  608 */     return (Model)newInstance(Model.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Engine createEngine() throws JAXBException {
/*  618 */     return (Engine)newInstance(Engine.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Position createPosition() throws JAXBException {
/*  628 */     return (Position)newInstance(Position.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FootnoteType createFootnoteType() throws JAXBException {
/*  638 */     return (FootnoteType)newInstance(FootnoteType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StdFootnoteType createStdFootnoteType() throws JAXBException {
/*  648 */     return (StdFootnoteType)newInstance(StdFootnoteType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PositionsType createPositionsType() throws JAXBException {
/*  658 */     return (PositionsType)newInstance(PositionsType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transmission createTransmission() throws JAXBException {
/*  668 */     return (Transmission)newInstance(Transmission.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Value createValue() throws JAXBException {
/*  678 */     return (Value)newInstance(Value.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Value createValue(String value) throws JAXBException {
/*  688 */     return (Value)new ValueImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Workshop createWorkshop() throws JAXBException {
/*  698 */     return (Workshop)newInstance(Workshop.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Workshop createWorkshop(String value) throws JAXBException {
/*  708 */     return (Workshop)new WorkshopImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comment createComment() throws JAXBException {
/*  718 */     return (Comment)newInstance(Comment.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comment createComment(String value) throws JAXBException {
/*  728 */     return (Comment)new CommentImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cplus createCplus() throws JAXBException {
/*  738 */     return (Cplus)newInstance(Cplus.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelType createModelType() throws JAXBException {
/*  748 */     return (ModelType)newInstance(ModelType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Positions createPositions() throws JAXBException {
/*  758 */     return (Positions)newInstance(Positions.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServType createServType() throws JAXBException {
/*  768 */     return (ServType)newInstance(ServType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServType createServType(String value) throws JAXBException {
/*  778 */     return (ServType)new ServTypeImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VINType createVINType() throws JAXBException {
/*  788 */     return (VINType)newInstance(VINType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BulletType createBulletType() throws JAXBException {
/*  798 */     return (BulletType)newInstance(BulletType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReleaseType createReleaseType() throws JAXBException {
/*  808 */     return (ReleaseType)newInstance(ReleaseType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DrvType createDrvType() throws JAXBException {
/*  818 */     return (DrvType)newInstance(DrvType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DrvType createDrvType(String value) throws JAXBException {
/*  828 */     return (DrvType)new DrvTypeImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InspType createInspType() throws JAXBException {
/*  838 */     return (InspType)newInstance(InspType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InspType createInspType(String value) throws JAXBException {
/*  848 */     return (InspType)new InspTypeImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Make createMake() throws JAXBException {
/*  858 */     return (Make)newInstance(Make.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Make createMake(String value) throws JAXBException {
/*  868 */     return (Make)new MakeImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VIN createVIN() throws JAXBException {
/*  878 */     return (VIN)newInstance(VIN.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PositionType createPositionType() throws JAXBException {
/*  888 */     return (PositionType)newInstance(PositionType.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter createParameter() throws JAXBException {
/*  898 */     return (Parameter)newInstance(Parameter.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phone createPhone() throws JAXBException {
/*  908 */     return (Phone)newInstance(Phone.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phone createPhone(String value) throws JAXBException {
/*  918 */     return (Phone)new PhoneImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Label createLabel() throws JAXBException {
/*  928 */     return (Label)newInstance(Label.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Label createLabel(String value) throws JAXBException {
/*  938 */     return (Label)new LabelImpl(value);
/*      */   }
/*      */   
/*      */   private static class GrammarInfoImpl extends GrammarInfo { private GrammarInfoImpl() {}
/*      */     
/*      */     public Class getDefaultImplementation(Class javaContentInterface) {
/*  944 */       return (Class)ObjectFactory.defaultImplementations.get(javaContentInterface);
/*      */     }
/*      */     
/*      */     public Class getRootElement(String uri, String local) {
/*  948 */       if ("" == uri && "Positions" == local) {
/*  949 */         return PositionsImpl.class;
/*      */       }
/*  951 */       if ("" == uri && "Header" == local) {
/*  952 */         return HeaderImpl.class;
/*      */       }
/*  954 */       if ("" == uri && "Text" == local) {
/*  955 */         return TextImpl.class;
/*      */       }
/*  957 */       if ("" == uri && "Title" == local) {
/*  958 */         return TitleImpl.class;
/*      */       }
/*  960 */       if ("" == uri && "Customer" == local) {
/*  961 */         return CustomerImpl.class;
/*      */       }
/*  963 */       if ("" == uri && "Confirmation" == local) {
/*  964 */         return ConfirmationImpl.class;
/*      */       }
/*  966 */       if ("" == uri && "Sign" == local) {
/*  967 */         return SignImpl.class;
/*      */       }
/*  969 */       if ("" == uri && "Transmission" == local) {
/*  970 */         return TransmissionImpl.class;
/*      */       }
/*  972 */       if ("" == uri && "Value" == local) {
/*  973 */         return ValueImpl.class;
/*      */       }
/*  975 */       if ("" == uri && "Check" == local) {
/*  976 */         return CheckImpl.class;
/*      */       }
/*  978 */       if ("" == uri && "InspType" == local) {
/*  979 */         return InspTypeImpl.class;
/*      */       }
/*  981 */       if ("" == uri && "Defects" == local) {
/*  982 */         return DefectsImpl.class;
/*      */       }
/*  984 */       if ("" == uri && "Engine" == local) {
/*  985 */         return EngineImpl.class;
/*      */       }
/*  987 */       if ("" == uri && "Make" == local) {
/*  988 */         return MakeImpl.class;
/*      */       }
/*  990 */       if ("" == uri && "Numberplate" == local) {
/*  991 */         return NumberplateImpl.class;
/*      */       }
/*  993 */       if ("" == uri && "DrvType" == local) {
/*  994 */         return DrvTypeImpl.class;
/*      */       }
/*  996 */       if ("" == uri && "Comment" == local) {
/*  997 */         return CommentImpl.class;
/*      */       }
/*  999 */       if ("" == uri && "Position" == local) {
/* 1000 */         return PositionImpl.class;
/*      */       }
/* 1002 */       if ("" == uri && "Cplus" == local) {
/* 1003 */         return CplusImpl.class;
/*      */       }
/* 1005 */       if ("" == uri && "Orderno" == local) {
/* 1006 */         return OrdernoImpl.class;
/*      */       }
/* 1008 */       if ("" == uri && "Model" == local) {
/* 1009 */         return ModelImpl.class;
/*      */       }
/* 1011 */       if ("" == uri && "VIN" == local) {
/* 1012 */         return VINImpl.class;
/*      */       }
/* 1014 */       if ("" == uri && "Release" == local) {
/* 1015 */         return ReleaseImpl.class;
/*      */       }
/* 1017 */       if ("" == uri && "Description" == local) {
/* 1018 */         return DescriptionImpl.class;
/*      */       }
/* 1020 */       if ("" == uri && "Mechanic" == local) {
/* 1021 */         return MechanicImpl.class;
/*      */       }
/* 1023 */       if ("" == uri && "Workshop" == local) {
/* 1024 */         return WorkshopImpl.class;
/*      */       }
/* 1026 */       if ("" == uri && "Bullet" == local) {
/* 1027 */         return BulletImpl.class;
/*      */       }
/* 1029 */       if ("" == uri && "Label" == local) {
/* 1030 */         return LabelImpl.class;
/*      */       }
/* 1032 */       if ("" == uri && "StdFootnote" == local) {
/* 1033 */         return StdFootnoteImpl.class;
/*      */       }
/* 1035 */       if ("" == uri && "Parameter" == local) {
/* 1036 */         return ParameterImpl.class;
/*      */       }
/* 1038 */       if ("" == uri && "Footnote" == local) {
/* 1039 */         return FootnoteImpl.class;
/*      */       }
/* 1041 */       if ("" == uri && "Km" == local) {
/* 1042 */         return KmImpl.class;
/*      */       }
/* 1044 */       if ("" == uri && "State" == local) {
/* 1045 */         return StateImpl.class;
/*      */       }
/* 1047 */       if ("" == uri && "ServType" == local) {
/* 1048 */         return ServTypeImpl.class;
/*      */       }
/* 1050 */       if ("" == uri && "PosFootnote" == local) {
/* 1051 */         return PosFootnoteImpl.class;
/*      */       }
/* 1053 */       if ("" == uri && "Serviceplan" == local) {
/* 1054 */         return ServiceplanImpl.class;
/*      */       }
/* 1056 */       if ("" == uri && "Phone" == local) {
/* 1057 */         return PhoneImpl.class;
/*      */       }
/* 1059 */       if ("" == uri && "Name" == local) {
/* 1060 */         return NameImpl.class;
/*      */       }
/* 1062 */       if ("" == uri && "Date" == local) {
/* 1063 */         return DateImpl.class;
/*      */       }
/* 1065 */       if ("" == uri && "Footer" == local) {
/* 1066 */         return FooterImpl.class;
/*      */       }
/* 1068 */       return null;
/*      */     }
/*      */     
/*      */     public String[] getProbePoints() {
/* 1072 */       return new String[] { "", "Positions", "", "Header", "", "Text", "", "Title", "", "Customer", "", "Confirmation", "", "Sign", "", "Transmission", "", "Value", "", "Check", "", "InspType", "", "Defects", "", "Engine", "", "Make", "", "Numberplate", "", "DrvType", "", "Comment", "", "Position", "", "Cplus", "", "Orderno", "", "Model", "", "VIN", "", "Release", "", "Description", "", "Mechanic", "", "Workshop", "", "Bullet", "", "Label", "", "StdFootnote", "", "Parameter", "", "Footnote", "", "Km", "", "State", "", "ServType", "", "PosFootnote", "", "Serviceplan", "", "Phone", "", "Name", "", "Date", "", "Footer" };
/*      */     } }
/*      */ 
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\ObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */