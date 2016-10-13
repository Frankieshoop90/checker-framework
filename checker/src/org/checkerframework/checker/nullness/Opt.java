package org.checkerframework.checker.nullness;

/*>>>
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
*/

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/**
 * Utility class for the Nullness Checker, providing every method in
 * {@link java.util.Optional}, but written for possibly-null references
 * rather than for the {@code Optional} type.
 * <p>
 *
 * To avoid the need to write the {@code Opt} class name at invocation sites, do:
 * <pre>import static org.checkerframework.checker.nullness.Opt.orElse;</pre>
 * or
 * <pre>import static org.checkerframework.checker.nullness.Opt.*;</pre>
 * <p>
 *
 * <b>Runtime Dependency</b>
 * <p>
 *
 * Please note that using this class introduces a runtime dependency.
 * This means that you need to distribute (or link to) the Checker
 * Framework, along with your binaries.
 *
 * To eliminate this dependency, you can simply copy this class into your
 * own project.
 *
 * @see java.util.Optional
 */
public final class Opt {

    private Opt() {
        throw new AssertionError("shouldn't be instantiated");
    }

    /**
     * If primary is non-null, returns it, otherwise throws NoSuchElementException.
     * @see java.util.Optional#get()
     */
    public static <T extends /*@Nullable*/ Object> /*@NonNull*/ T get(T primary) {
        if (primary == null) {
            throw new NoSuchElementException("No value present");
        }
        return primary;
    }

    /**
     * Returns true if primary is non-null, false if primary is null.
     * @see java.util.Optional#isPresent()
     */
    @EnsuresNonNullIf(expression = "#1", result = true)
    public boolean isPresent(/*@Nullable*/ Object primary) {
        return primary != null;
    }

    /**
     * If primary is non-null, invoke the specified consumer with the value, otherwise do nothing.
     * @see java.util.Optional#ifPresent(Consumer)
     */
    public static <T extends /*@Nullable*/ Object> void ifPresent(
            T primary, Consumer<? super T> consumer) {
        if (primary != null) {
            consumer.accept(primary);
        }
    }

    /**
     * If primary is non-null, and its value matches the given predicate, return the value.
     * If primary is null or its non-null value does not match the predicate, return null.
     * @see java.util.Optional#filter(Predicate)
     */
    public static <T extends /*@Nullable*/ Object> /*@Nullable*/ T filter(
            T primary, Predicate<? super T> predicate) {
        if (primary == null) {
            return null;
        } else {
            return predicate.test(primary) ? primary : null;
        }
    }

    /**
     * If primary is non-null, apply the provided mapping function to it and return the result.
     * If primary is null, return null.
     * @see java.util.Optional#map(Function)
     */
    public static <T extends /*@Nullable*/ Object, U extends /*@Nullable*/ Object>
            /*@Nullable*/ U map(T primary, Function<? super T, ? extends U> mapper) {
        if (primary == null) {
            return null;
        } else {
            return mapper.apply(primary);
        }
    }

    // flatMap would have the same signature and implementation as map

    /**
     * Return primary if it is non-null.
     * If primary is null, return other.
     * @see java.util.Optional#orElse(Object)
     */
    public static <T extends /*@Nullable*/ Object> /*@NonNull*/ T orElse(
            T primary, /*@NonNull*/ T other) {
        return primary != null ? primary : other;
    }

    /**
     * Return primary if it is non-null.
     * If primary is null, invoke {@code other} and return the result of that invocation.
     * @see java.util.Optional#orElseGet(Supplier)
     */
    public static <T extends /*@Nullable*/ Object> /*@NonNull*/ T orElseGet(
            T primary, Supplier<? extends /*@NonNull*/ T> other) {
        return primary != null ? primary : other.get();
    }

    /**
     * Return primary if it is non-null.
     * If primary is null, return an exception to be created by the provided supplier.
     * @see java.util.Optional#orElseThrow(Supplier)
     */
    public static <T extends /*@Nullable*/ Object, X extends /*@NonNull*/ Throwable>
            /*@NonNull*/ T orElseThrow(
            T primary, Supplier<? extends /*@NonNull*/ X> exceptionSupplier) throws X {
        if (primary != null) {
            return primary;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
