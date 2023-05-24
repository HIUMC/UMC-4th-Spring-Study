package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient{
    private String url;
    @PostConstruct
   public void init(){
       System.out.println("NetworkClient.init");
       connect();
       disconnect();
   }
   @PreDestroy
   public void close(){
       System.out.println("NetworkClient.close");
       disconnect();
   }


    public NetworkClient(){
        System.out.println("생성자 호출,url= "+url);
    }
    public void setUrl(String url){
        this.url=url;
    }
    public void connect(){
        System.out.println("connect = " + url);
    }
    public void call(String message){
        System.out.println("call: "+url+" message= "+message);
    }
    public void disconnect(){
        System.out.println("close: "+url);
    }
}
