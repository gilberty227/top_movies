package br.com.topmovies.data;

import android.content.Context;
import br.com.topmovies.data.models.Genres;
import br.com.topmovies.data.models.Movies;
import io.realm.RealmResults;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Mock
    private Context context;

    private Repository repository = new Repository();
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private ArrayList<Genres> listGenres = new ArrayList<>();

    private void builderListMovies() {
        Movies movieOne = new Movies();
        movieOne.setId(1);
        movieOne.setTitle("Exterminador do Futuro");
        movieOne.setRelease_date("20/05/2020");
        movieOne.getGenre_ids().add(1);
        movieOne.getGenre_ids().add(2);
        movieOne.setOverview("Antigo, porém legal");
        movieOne.setBackdrop_path("hdjkaskjrjh.jpg");
        movieOne.setPoster_path("mcnxzncbnm.jpg");

        Movies movieTwo = new Movies();
        movieTwo.setId(1);
        movieTwo.setTitle("Se meu fusca falasse");
        movieTwo.setRelease_date("14/01/2028");
        movieTwo.getGenre_ids().add(1);
        movieTwo.getGenre_ids().add(2);
        movieTwo.setOverview("É um fusca que se comunica");
        movieTwo.setBackdrop_path("hdjddddkaskjrjh.jpg");
        movieTwo.setPoster_path("mcnxzddddncbnm.jpg");

        listMovies.add(movieOne);
        listMovies.add(movieTwo);
    }

    private void builderListGenres() {
        Genres genreOne = new Genres();
        genreOne.setId(1);
        genreOne.setName("Acao");

        Genres genreTwo = new Genres();
        genreTwo.setId(1);
        genreTwo.setName("Drama");

        listGenres.add(genreOne);
        listGenres.add(genreTwo);
    }

    @Before
    public void setup() {
        builderListMovies();
        builderListGenres();
    }

    @Test
    public void getListAllMovies() {
        for (Genres genre : listGenres) {
            repository.saveData(genre);
        }

        for (Movies movie : listMovies) {
            repository.saveData(movie);
        }

        RealmResults<Movies> listMoviesDB = repository.getListAllMovies();
        Assert.assertTrue(listMoviesDB.size() > 0);
    }
}
