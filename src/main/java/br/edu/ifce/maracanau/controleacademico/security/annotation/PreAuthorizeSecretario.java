package br.edu.ifce.maracanau.controleacademico.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ROLE_' + T(br.edu.ifce.maracanau.controleacademico.model.enums.PerfilUsuario).SECRETARIO.name())")
public @interface PreAuthorizeSecretario {
}
