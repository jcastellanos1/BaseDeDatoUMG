/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.umg.bdd;


import com.edu.umg.DTO.PersonaDTO;
import static com.edu.umg.bdd.ConnectionBdd.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wilson Aguin
 */
public class DMLBdd    {
        private static DMLBdd dml;
        private static Connection connection = ConnectionBdd.getConnection();

    public DMLBdd() {}

    public static DMLBdd getDml() {
        if (dml == null) {
            dml = new DMLBdd();
        }
        return dml;
    }
    public static List<PersonaDTO> listaPersona() throws SQLException{
     List<PersonaDTO>list= new ArrayList<PersonaDTO>();
       if(connection !=null){
           try {
               Statement stmt=connection.createStatement();
               String query="Select *from persona";
               ResultSet rs =stmt.executeQuery(query);
               while(rs.next()){
                   PersonaDTO per = new PersonaDTO();
                 per.setIdPersona(rs.getInt("id_persona"));
                 per.setNombre(rs.getString("nombre"));
                 per.setApellido(rs.getString("apellido"));
                 per.setTelefono(rs.getInt("telefono"));
                 per.setCorreo(rs.getString("correo"));
                 per.setEstado(rs.getInt("estado"));
                 list.add(per);
               }
           } catch (Exception ex) {
               connection.close();
               System.out.println("Error al realizar la consulta.." + ex);
           }
       }
       
       
       return list;
   }
   
    
    public List<PersonaDTO> buscarPersona(String nombre, String apellido) {
    List<PersonaDTO> personas = new ArrayList<>();
    Connection connection = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;

    try {
        connection = ConnectionBdd.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM persona WHERE nombre = ? AND apellido = ?";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, nombre);
            pstm.setString(2, apellido);
            rs = pstm.executeQuery();

            while (rs.next()) {
                PersonaDTO persona = new PersonaDTO();
                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setTelefono(rs.getInt("telefono"));
                persona.setCorreo(rs.getString("correo"));
                persona.setEstado(rs.getInt("estado"));
                personas.add(persona);
            }
        }
    } catch (Exception ex) {
        System.out.println("Error al buscar la persona: " + ex);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return personas;
}

   public void insertarPersona(PersonaDTO persona) throws SQLException {
    String sql = "INSERT INTO persona (nombre, apellido, telefono, correo, estado) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, persona.getNombre());
        stmt.setString(2, persona.getApellido());
        stmt.setInt(3, persona.getTelefono());
        stmt.setString(4, persona.getCorreo());
        stmt.setInt(5, persona.getEstado());
        
        stmt.executeUpdate();
    } catch (SQLException ex) {
        System.out.println("Error al insertar la persona: " + ex.getMessage());
    }
}


     
public void modificarPersona(PersonaDTO persona) throws SQLException {
    String sql = "UPDATE persona SET nombre = ?, apellido = ?, telefono = ?, correo = ?, estado = ? WHERE id_persona = ?";
    
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, persona.getNombre());
        stmt.setString(2, persona.getApellido());
        stmt.setInt(3, persona.getTelefono()); // Asegúrate de que este sea el tipo correcto
        stmt.setString(4, persona.getCorreo());
        stmt.setInt(5, persona.getEstado());
        stmt.setInt(6, persona.getIdPersona()); // Usa el ID del PersonaDTO

        System.out.println("Ejecutando actualización para ID: " + persona.getIdPersona());
        stmt.executeUpdate();
    }
}

public void eliminarPersona(PersonaDTO persona) throws SQLException {
    String sql = "DELETE FROM persona WHERE id_persona  = ?";
    
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, persona.getIdPersona());

        System.out.println("Ejecutando eliminación para ID: " + persona.getIdPersona());
        stmt.executeUpdate();
    }
}


    /*public static void main(String[] args) throws SQLException {
        // Test de la conexión
       
        System.out.println("Entrando a ver los registros");
        
        List<PersonaDTO>list= new ArrayList<PersonaDTO>();
        list=listaPersona();
        
        for (PersonaDTO personaDTO : list) {
            System.out.println("los datos de la lista es: ");
            System.out.println("id_persiona" +personaDTO.getIdPersona());
            System.out.println("NOmbre: "+personaDTO.getNombre()+" "+personaDTO.getApellido());
        }
                
       
    }*/

     
     
 
}
