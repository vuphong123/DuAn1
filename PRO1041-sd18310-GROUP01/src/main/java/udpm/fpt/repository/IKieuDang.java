/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udpm.fpt.model.KieuDang;

/**
 *
 * @author PHONG PC
 */
public interface IKieuDang extends JpaRepository<KieuDang, Integer>{
    
}
