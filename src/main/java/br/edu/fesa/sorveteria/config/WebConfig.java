package br.edu.fesa.sorveteria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Mapeia a URL raiz "/" diretamente para a view "lista" (seu lista.html)
        registry.addViewController("/").setViewName("lista");
        // Remova o mapeamento para /index, já que o index.html foi removido e não será mais a home.
        // Se você quiser que /index ainda leve para a lista de sorvetes, adicione:
        // registry.addViewController("/index").setViewName("lista");
    }
}
