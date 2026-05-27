package com.educacionit.java18tpi.interfaces;

import java.util.List;

public interface DAO <E,K> {

	List<E> getAll();
	E getById(K id);
	void insert (E objeto);
	void update(E objeto, K id);
	void delete(K id);
	boolean exists(K id);
}
