package br.com.products;

import org.easymock.EasyMockSupport;
import org.junit.Before;

public abstract class AbstractClassTest<T> extends EasyMockSupport {

	public abstract T createObject();

	@Before
	public abstract void startUp();

}
