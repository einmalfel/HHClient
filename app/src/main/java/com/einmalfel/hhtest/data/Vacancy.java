package com.einmalfel.hhtest.data;

import android.support.annotation.Nullable;

import java.util.Date;
import java.util.Map;

public class Vacancy {
  public final long id;
  public final String description;
  @Nullable
  public final String branded_description;
  public final Skill[] key_skills;
  public final Schedule schedule;
  public final boolean accept_handicapped;
  public final boolean accept_kids;
  public final Experience experience;
  @Nullable
  public final Address address;
  public final String alternate_url;
  public final String apply_alternate_url;
  @Nullable
  public final String code;
  @Nullable
  public final Department department;
  @Nullable
  public final Employment employment;
  @Nullable
  public final Salary salary;
  public final boolean archived;
  public final String name;
  public final Area area;
  public final Date published_at;
  public final Employer employer;
  public final boolean response_latter_required;
  public final Type type;
  @Nullable
  public final String response_url;
  @Nullable
  public final Test test;
  public final Specialization[] specializations;
  public final Contact[] contacts;
  public final BillingType billing_type;
  public final boolean allow_messages;
  public final boolean premium;
  @Nullable
  public final Snippet snippet;

  public Vacancy(long id, String description, @Nullable String branded_description,
                 Skill[] key_skills, Schedule schedule,
                 boolean accept_handicapped,
                 boolean accept_kids, Experience experience, @Nullable Address address,
                 String alternate_url, String apply_alternate_url, @Nullable String code,
                 @Nullable Department department, @Nullable Employment employment,
                 @Nullable Salary salary, boolean archived, String name, Area area,
                 Date published_at, Employer employer, boolean response_latter_required,
                 Type type, @Nullable String response_url, @Nullable Test test,
                 Specialization[] specializations, Contact[] contacts, BillingType billing_type,
                 boolean allow_messages, boolean premium, @Nullable Snippet snippet) {
    this.id = id;
    this.description = description;
    this.branded_description = branded_description;
    this.key_skills = key_skills;
    this.schedule = schedule;
    this.accept_handicapped = accept_handicapped;
    this.accept_kids = accept_kids;
    this.experience = experience;
    this.address = address;
    this.alternate_url = alternate_url;
    this.apply_alternate_url = apply_alternate_url;
    this.code = code;
    this.department = department;
    this.employment = employment;
    this.salary = salary;
    this.archived = archived;
    this.name = name;
    this.area = area;
    this.published_at = published_at;
    this.employer = employer;
    this.response_latter_required = response_latter_required;
    this.type = type;
    this.response_url = response_url;
    this.test = test;
    this.specializations = specializations;
    this.contacts = contacts;
    this.billing_type = billing_type;
    this.allow_messages = allow_messages;
    this.premium = premium;
    this.snippet = snippet;
  }

  public class Snippet {
    public final String requirement;
    public final String responsibility;

    public Snippet(String requirement, String responsibility) {
      this.requirement = requirement;
      this.responsibility = responsibility;
    }
  }

  public class Schedule {
    public final String id;
    public final String name;

    public Schedule(String id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  public class Experience {
    public final String id;
    public final String name;

    public Experience(String id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  public class Department {
    public final String id;
    public final String name;

    public Department(String id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  public class Employment {
    public final String id;
    public final String name;

    public Employment(String id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  public class BillingType {
    public final String id;
    public final String name;

    public BillingType(String id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  public class Type {
    public final String id;
    public final String name;

    public Type(String id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  public class Area {
    public final String id;
    public final String name;
    public final String url;

    public Area(String id, String name, String url) {
      this.id = id;
      this.name = name;
      this.url = url;
    }
  }

  public class Test {
    private final boolean required;

    public Test(boolean required) {
      this.required = required;
    }
  }

  public class Salary {
    @Nullable
    public final Long from;
    @Nullable
    public final Long to;
    public final String currency;
    @Nullable
    public final Boolean gross;

    public Salary(@Nullable Long from, @Nullable Long to, String currency, Boolean gross) {
      this.from = from;
      this.to = to;
      this.currency = currency;
      this.gross = gross;
    }
  }

  public class Specialization {
    public final long profarea_id;
    public final String profarea_name;
    public final long id;
    public final String name;

    public Specialization(long profarea_id, String profarea_name, long id, String name) {
      this.profarea_id = profarea_id;
      this.profarea_name = profarea_name;
      this.id = id;
      this.name = name;
    }
  }

  public class Employer {
    public final Map<String, String> logo_urls;
    public final String name;
    public final String url;
    public final String alternateUrl;
    public final long id;
    public final boolean trusted;
    public final boolean blacklisted;

    public Employer(Map<String, String> logo_urls, String name, String url,
                    String alternateUrl, long id, boolean trusted, boolean blacklisted) {
      this.logo_urls = logo_urls;
      this.name = name;
      this.url = url;
      this.alternateUrl = alternateUrl;
      this.id = id;
      this.trusted = trusted;
      this.blacklisted = blacklisted;
    }
  }

  public class Contact {
    @Nullable
    public final String name;
    @Nullable
    public final String email;
    public final String number;
    @Nullable
    public final String comment;

    public Contact(String name, String email, String number, @Nullable String comment) {
      this.name = name;
      this.email = email;
      this.number = number;
      this.comment = comment;
    }
  }

  public class MetroStation {
    public final String stationId;
    public final String stationName;
    public final String lineId;
    public final String line_name;
    public final Double lat;
    public final Double lng;

    public MetroStation(String stationId, String stationName, String lineId,
                        String line_name, Double lat, Double lng) {
      this.stationId = stationId;
      this.stationName = stationName;
      this.lineId = lineId;
      this.line_name = line_name;
      this.lat = lat;
      this.lng = lng;
    }
  }

  public class Address {
    public final String city;
    public final String street;
    public final String building;
    public final Double lat;
    public final Double lng;
    public final String raw;
    public final MetroStation[] metro_stations;

    public Address(String city, String street, String building, Double lat, Double lng,
                   String raw, MetroStation[] metro_stations) {
      this.city = city;
      this.street = street;
      this.building = building;
      this.lat = lat;
      this.lng = lng;
      this.raw = raw;
      this.metro_stations = metro_stations;
    }
  }

  public class Skill {
    public final String string;

    public Skill(String string) {
      this.string = string;
    }
  }
}