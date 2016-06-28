package ar.edu.utn.frba.dds.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ar.edu.utn.frba.dds")
public class TerminalConfiguration extends WebMvcConfigurerAdapter {

    //TODO acá poner el directorio donde finalmente estén la/las páginas
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");
//        registry.viewResolver(viewResolver);
//    }

    //TODO acá poner el directorio donde estén los js y todo lo web
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
//        registry.addResourceHandler("fonts/**").addResourceLocations("/static/fonts/");
//        registry.addResourceHandler("/vendor/**").addResourceLocations("/vendor/");
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/static/resources/");
    }
}
