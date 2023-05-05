package hello.core.singleton;

public class SingletonService {
    private static SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService(){
        //이렇게 생성자를 private으로 만들어서 아무렇게나 생성 못하게 함
    }
}
