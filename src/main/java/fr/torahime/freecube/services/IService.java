package fr.torahime.freecube.services;

public interface IService<T> {

    public String convertToJson(T object);
    public boolean create(T object);
    public boolean update(T object);
    public boolean delete(T object);
    public T get(String id);
}
