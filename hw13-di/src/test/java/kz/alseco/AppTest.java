package kz.alseco;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import kz.alseco.appcontainer.AppComponentsContainerImpl;
import kz.alseco.appcontainer.api.AppComponentsContainer;
import kz.alseco.config.AppConfig;
import kz.alseco.services.*;

import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    //@Disabled //надо удалить
    @DisplayName("Из контекста тремя способами должен корректно доставаться компонент с проставленными полями")
    @ParameterizedTest(name = "Достаем по: {0}")
    @CsvSource(value = {"GameProcessor, kz.alseco.services.GameProcessor",
            "GameProcessorImpl, kz.alseco.services.GameProcessor",
            "gameProcessor, kz.alseco.services.GameProcessor",

            "IOService, kz.alseco.services.IOService",
            "IOServiceStreams, kz.alseco.services.IOService",
            "ioService, kz.alseco.services.IOService",

            "PlayerService, kz.alseco.services.PlayerService",
            "PlayerServiceImpl, kz.alseco.services.PlayerService",
            "playerService, kz.alseco.services.PlayerService",

            "EquationPreparer, kz.alseco.services.EquationPreparer",
            "EquationPreparerImpl, kz.alseco.services.EquationPreparer",
            "equationPreparer, kz.alseco.services.EquationPreparer"
    })
    public void shouldExtractFromContextCorrectComponentWithNotNullFields(String classNameOrBeanId, Class<?> rootClass) throws Exception {
        var ctx = new AppComponentsContainerImpl(AppConfig.class);

        assertThat(classNameOrBeanId).isNotEmpty();
        Object component;
        if (classNameOrBeanId.charAt(0) == classNameOrBeanId.toUpperCase().charAt(0)) {
            Class<?> gameProcessorClass = Class.forName("kz.alseco.services." + classNameOrBeanId);
            assertThat(rootClass).isAssignableFrom(gameProcessorClass);

            component = ctx.getAppComponent(gameProcessorClass);
        } else {
            component = ctx.getAppComponent(classNameOrBeanId);
        }
        assertThat(component).isNotNull();
        assertThat(rootClass).isAssignableFrom(component.getClass());

        var fields = Arrays.stream(component.getClass().getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toList());

        for (var field: fields){
            var fieldValue = field.get(component);
            assertThat(fieldValue).isNotNull().isInstanceOfAny(IOService.class, PlayerService.class,
                    EquationPreparer.class, PrintStream.class, Scanner.class);
        }

    }
}