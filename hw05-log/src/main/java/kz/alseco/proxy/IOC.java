package kz.alseco.proxy;

import kz.alseco.annotations.Log;
import kz.alseco.domain.Constants;
import kz.alseco.utils.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class IOC {

	private static final Set<String> methodsForLogging = new HashSet<>();

	public static <T> T getInstance(final T obj) {
		final Class<?> clazz = obj.getClass();
		final Method[] methods = clazz.getMethods();
		final Set<Method> annotatedMethods = ReflectionUtils.filterMethodsByAnnotation(methods, Log.class);

		if (annotatedMethods.isEmpty()) {
			throw new IllegalArgumentException("Not found annotation: " + Log.class.getCanonicalName());
		}

		final Set<String> namesAndParams = annotatedMethods.stream().map(ReflectionUtils::extractNameAndParams).collect(Collectors.toSet());

		methodsForLogging.addAll(namesAndParams);

		return (T) Proxy.newProxyInstance(IOC.class.getClassLoader(), clazz.getInterfaces(), new LogInvocationHandler<>(obj));
	}

	private static <T> void printMethodInfo(final Method method, final Object[] args) throws Exception {
		final String methodName = method.getName();
		System.out.println(String.format(Constants.METHOD_INFO_TEMPLATE_MSG, methodName, Arrays.toString(args)));
	}

	private static class LogInvocationHandler<T> implements InvocationHandler {

		private final T instance;

		private LogInvocationHandler(final T instance) {
			this.instance = instance;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			final String nameAndParams = ReflectionUtils.extractNameAndParams(method);

			if (methodsForLogging.contains(nameAndParams)) {
				printMethodInfo(method, args);
			}

			return method.invoke(instance, args);
		}
	}
}
