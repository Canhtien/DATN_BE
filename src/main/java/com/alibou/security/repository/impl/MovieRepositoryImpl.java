//package com.alibou.security.repository.impl;
//
//import com.alibou.security.entity.Movie;
//import com.alibou.security.model.response.MovieResponse;
//import com.alibou.security.repository.MovieRepository;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.stereotype.Repository;
////import com.alibou.etc.xlibrary.core.repositories.CommonDataBaseRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
////CommonDataBaseRepository
//@Repository
//public class MovieRepositoryImpl implements MovieRepository {
//    @Override
//    public boolean existsByTitle(String title) {
//        return false;
//    }
//
//    @Override
//    public Optional<Movie> findById(Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public Page<Movie> findAllWithPagination(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<MovieResponse> findTopWithPagination(Pageable pageable) {
//        return List.of();
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends Movie> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Movie> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<Movie> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public Movie getOne(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public Movie getById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public Movie getReferenceById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public <S extends Movie> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Movie> List<S> findAll(Example<S> example) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Movie> List<S> findAll(Example<S> example, Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Movie> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Movie> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Movie> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Movie, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends Movie> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Movie> List<S> saveAll(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public List<Movie> findAll() {
//        return List.of();
//    }
//
//    @Override
//    public List<Movie> findAllById(Iterable<Long> longs) {
//        return List.of();
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(Movie entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Movie> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<Movie> findAll(Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public Page<Movie> findAll(Pageable pageable) {
//        return null;
//    }
//}
