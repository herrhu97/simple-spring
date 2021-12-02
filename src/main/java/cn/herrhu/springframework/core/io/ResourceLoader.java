package cn.herrhu.springframework.core.io;

public interface ResourceLoader {
    /**
     * Pseudo URL Prefix for loading from the class path: "classpath:"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
