/**
 *
 */
package com.task;

import java.util.Arrays;

/**
 * @author ssathinadhan
 */
public class Task {

    private String className = null;

    private String[] methodNames = null;

    private boolean isError = false;

    private Exception exception = null;

    /**
     * @param className
     * @param methodNames
     */
    public Task(final String className, final String[] methodNames) {
        super();
        this.className = className;
        this.methodNames = methodNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        final Task other = (Task) obj;
        if (this.className == null) {
            if (other.className != null) {
                return false;
            }
        } else if (!this.className.equals(other.className)) {
            return false;
        }
        if (!Arrays.equals(this.methodNames, other.methodNames)) {
            return false;
        }
        return true;
    }

    public String getClassName() {
        return this.className;
    }

    public Exception getException() {
        return this.exception;
    }

    /**
     * @return the methodNames
     */
    public String[] getMethodNames() {
        return this.methodNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.className == null) ? 0 : this.className.hashCode());
        result = (prime * result) + Arrays.hashCode(this.methodNames);
        return result;
    }

    public boolean isError() {
        return this.isError;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public void setError(final boolean isError) {
        this.isError = isError;
    }

    public void setException(final Exception exception) {
        this.exception = exception;
    }

    /**
     * @param methodNames
     * the methodNames to set
     */
    public void setMethodNames(final String[] methodNames) {
        this.methodNames = methodNames;
    }

}
