package org.example.persistence.repository.implementation;

import org.example.persistence.entity.Address;
import org.example.persistence.entity.HOD;
import org.example.persistence.entity.Phone;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.HODRepository;
import java.util.List;

/**
 * <h3>Class HODRepositoryImpl</h3>
 * This is a {@link HODRepository} implementation which also extends {@link EmployeeRepositoryImpl}.
 */
public class HODRepositoryImpl extends EmployeeRepositoryImpl<HOD> implements HODRepository {

    @Override
    public HOD get(String email) throws NotFoundException {

        return super.get(email, HOD.class);
    }

    @Override
    public List<HOD> getAll() {

        return super.getAll(HOD.class);
    }

    @Override
    public void setForename(String email, String forename) throws NotFoundException {

        super.setForename(email, forename, HOD.class);
    }

    @Override
    public void setSurname(String email, String surname) throws NotFoundException {

        super.setSurname(email, surname, HOD.class);
    }

    @Override
    public void addAddress(String email, Address address) throws NotFoundException {

        super.addAddress(email, address, HOD.class);
    }

    @Override
    public void removeAddress(String email, Address address) throws NotFoundException {

        super.removeAddress(email, address, HOD.class);
    }

    @Override
    public void updateAddress(String email, Address oldAddress, Address newAddress) throws NotFoundException {

        super.updateAddress(email, oldAddress, newAddress, HOD.class);
    }

    @Override
    public void addPhone(String email, Phone phone) throws NotFoundException {

        super.addPhone(email, phone, HOD.class);
    }

    @Override
    public void removePhone(String email, Phone phone) throws NotFoundException {

        super.removePhone(email, phone, HOD.class);
    }

    @Override
    public void updatePhone(String email, Phone oldPhone, Phone newPhone) throws NotFoundException {

        super.updatePhone(email, oldPhone, newPhone, HOD.class);
    }
}
