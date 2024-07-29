package dio.projeto.base.controller;

import dio.projeto.base.model.Compra;
import dio.projeto.base.repository.CompraRepository;
import dio.projeto.base.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Compra> criarCompra(@RequestBody Compra compra) {
        return usuarioRepository.findById(compra.getUsuario().getId())
                .map(usuario -> {
                    compra.setUsuario(usuario);
                    Compra salvo = compraRepository.save(compra);
                    return new ResponseEntity<>(salvo, HttpStatus.CREATED);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> buscarCompra(@PathVariable Long id) {
        return compraRepository.findById(id)
                .map(compra -> new ResponseEntity<>(compra, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
