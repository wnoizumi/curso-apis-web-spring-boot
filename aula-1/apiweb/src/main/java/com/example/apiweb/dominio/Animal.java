package com.example.apiweb.dominio;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Animal
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String nome;
  private String tutor;
  private String especie;

  public long getId()
  {
    return id;
  }

  private void setId(final long id)
  {
    this.id = id;
  }

  public String getNome()
  {
    return nome;
  }

  public void setNome(final String nome)
  {
    this.nome = nome;
  }

  public String getTutor()
  {
    return tutor;
  }

  public void setTutor(final String tutor)
  {
    this.tutor = tutor;
  }

  public String getEspecie()
  {
    return especie;
  }

  public void setEspecie(final String especie)
  {
    this.especie = especie;
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    final Animal animal = (Animal) o;
    return id == animal.id;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id);
  }
}
