package kz.alseco.dataprocessor;

import kz.alseco.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
