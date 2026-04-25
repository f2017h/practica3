error id: file:///C:/Users/Felicia/Coding/Telematic%20programming/practica3/practica3/src/main/java/com/example/practica3/entity/CarritoLinea.java
file:///C:/Users/Felicia/Coding/Telematic%20programming/practica3/practica3/src/main/java/com/example/practica3/entity/CarritoLinea.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[65,1]

error in qdox parser
file content:
```java
offset: 1364
uri: file:///C:/Users/Felicia/Coding/Telematic%20programming/practica3/practica3/src/main/java/com/example/practica3/entity/CarritoLinea.java
text:
```scala
package com.example.practica3.entity;

import com.example.practica3.entity.Carrito;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class CarritoLinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idCarrito;

    @ManyToOne
    @JoinColumn(name = "idCarrito")
    @JsonIgnore
    private Carrito carrito;


    @Column(nullable = false)
    private Long idArticulo;

    @Column
    public Long precioUnitario;
    @Column
    public int numeroUnidades;
    @Column
    public Long costaLinea;



    public void incrementArticulo(int num){
        numeroUnidades += num;
    }

    public Long getIdArticulo(){
        return this.idArticulo;
    }

    public Long getPrecioUnitario() { return this.precioUnitario;}

    public void setPrecioUnitario(Long num){ this.precioUnitario = num;}


    public void setCarrito(Carrito c){
        this.carrito = c;
    }

    public Long getCostaLinea() {
        return numeroUnidades * precioUnitario;
    }

    public void setCostaLinea(Long sum){
        this.costaLinea = sum;
    }

    public void setIdArticulo(Long idArticulo) {
    this.idArticulo = idArticulo;
}
}
@@
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:560)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:691)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:688)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:630)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:628)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1313)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:688)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:940)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:691)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:500)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:842)
```
#### Short summary: 

QDox parse error in file:///C:/Users/Felicia/Coding/Telematic%20programming/practica3/practica3/src/main/java/com/example/practica3/entity/CarritoLinea.java