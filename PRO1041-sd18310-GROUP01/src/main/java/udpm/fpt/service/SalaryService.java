/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import static udpm.fpt.Application.getBean;

import udpm.fpt.model.Salary;
import udpm.fpt.model.UserDetails;
import udpm.fpt.repository.ISalary;

/**
 *
 * @author Administrator
 */
public class SalaryService {

    private final ISalary iSalary = getBean(ISalary.class);

    public List<Salary> getSalaryList() {
        return this.iSalary.findAll().stream()
                .filter(user -> user.getStatus().equalsIgnoreCase("Active"))
                .collect(Collectors.toList());
    }

    public CompletableFuture<List<Salary>> getList() {
        return CompletableFuture.supplyAsync(() -> {
            List<Salary> all = this.iSalary.findAll().stream()
                    .filter(user -> user.getStatus().equalsIgnoreCase("Active"))
                    .collect(Collectors.toList());
            return all;
        }, Executors.newCachedThreadPool());
    }
    
    public boolean addNewSalary(Salary obj) {
        return this.iSalary.save(obj) != null;
    }
    
    public void deleteSalary(Salary obj) {
        this.iSalary.delete(obj);
    }
    
    public boolean updateSalary(Salary obj) {
        return this.iSalary.save(obj) != null;
    }

}
