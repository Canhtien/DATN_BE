//package com.alibou.security.repository.impl;
//
//import com.alibou.security.entity.User;
//import com.alibou.security.repository.UserRepository;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//@Repository
//public class UserRepositoryImpl implements UserRepository {
//    @Override
//    public Optional<User> findByEmail(String email) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> findByUsername(String username) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> findById(Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> findByUsernameOrEmail(String username, String email) {
//        return Optional.empty();
//    }
//
//    @Override
//    public void deactivateUserById(Long id) {
//
//    }
//
//    @Override
//    public Page<User> findAllWithPagination(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends User> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<User> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public User getOne(Integer integer) {
//        return null;
//    }
//
//    @Override
//    public User getById(Integer integer) {
//        return null;
//    }
//
//    @Override
//    public User getReferenceById(Integer integer) {
//        return null;
//    }
//
//    @Override
//    public <S extends User> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends User> List<S> findAll(Example<S> example) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends User> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends User> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends User> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends User> List<S> saveAll(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public Optional<User> findById(Integer integer) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Integer integer) {
//        return false;
//    }
//
//    @Override
//    public List<User> findAll() {
//        return List.of();
//    }
//
//    @Override
//    public List<User> findAllById(Iterable<Integer> integers) {
//        return List.of();
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Integer integer) {
//
//    }
//
//    @Override
//    public void delete(User entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Integer> integers) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends User> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<User> findAll(Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public Page<User> findAll(Pageable pageable) {
//        return null;
//    }
//}
