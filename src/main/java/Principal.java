
        //3
    private void btnCompra1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        
        String nombre = nombreUser.getText().trim();
        String apellido = apellidoUser.getText().trim();
        String cedula = cedulaUser.getText().trim();

        // Validación de campos vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos antes de continuar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de nombre y apellido (solo letras y espacios)
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(null, "El nombre y apellido solo deben contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación de cédula (exactamente 10 dígitos)
        if (!cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "La cédula debe contener exactamente 10 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar a la función para agregar productos
        agregarProductos(nombre, apellido, cedula);
        
    }                                          
    //4 y 5
    private void agregarProductos(String nombre, String apellido, String cedula) {
        ArrayList<String> listaProductos = new ArrayList<>();
        HashSet<String> productosSeleccionados = new HashSet<>();
        float precioTotal = 0;

        while (true) {
            String codigoProducto = JOptionPane.showInputDialog(null, "Ingrese el codigo del producto:", "Codigo del Producto", JOptionPane.QUESTION_MESSAGE);
            if (codigoProducto == null || codigoProducto.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Codigo invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Evita la selección repetida de productos
            if (!productosSeleccionados.add(codigoProducto)) {
                JOptionPane.showMessageDialog(null, "Este producto ya ha sido seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Buscar el producto en la lista
            String productoEncontrado = null;
            for (int i = 0; i < modeloLista.getSize(); i++) {
                String item = modeloLista.getElementAt(i);
                if (item.startsWith(codigoProducto + " |")) {
                    productoEncontrado = item;
                    break;
                }
            }

            if (productoEncontrado == null) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            String[] datos = productoEncontrado.split(" \\| ");
            try {
                Producto producto = new Producto(datos[0], datos[1], Float.parseFloat(datos[2].trim()), Integer.parseInt(datos[3].trim()));

                // Solicitar cantidad del producto
                int cantidad = obtenerCantidadValida(producto);
                if (cantidad == -1) {
                    productosSeleccionados.remove(codigoProducto);
                    continue;
                }

                producto.setCantidad(cantidad);
                listaProductos.add(producto.getCodigo() + ":" + producto.getNombre() + ":" + producto.getPrecio() + ":" + cantidad);
                precioTotal += producto.getPrecio() * cantidad;

                // Confirmación de agregar más productos
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea agregar otro producto?", "Confirmar compra", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.NO_OPTION) {
                    break;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error al procesar los datos del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!listaProductos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Compra finalizada. Generando factura...");
            generarFactura(listaProductos, nombre, apellido, cedula);
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado productos a la compra.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //6
    private int obtenerCantidadValida(Producto producto) {
        while (true) {
            String cantidadStr = JOptionPane.showInputDialog(null, "¿Cuántos productos deseas comprar?", "Cantidad", JOptionPane.QUESTION_MESSAGE);

            if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }

            try {
                int cantidad = Integer.parseInt(cantidadStr.trim());
                if (cantidad > 0 && cantidad <= producto.getCantidad()) {
                    return cantidad;
                } else {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida o fuera de stock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //7
    private void btnCompra2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        JTextField idField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField precioField = new JTextField();
        JTextField cantidadField = new JTextField();

        Object[] mensaje = {
            "ID del producto:", idField,
            "Nombre:", nombreField,
            "Precio:", precioField,
            "Cantidad:", cantidadField
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Nuevo Producto", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String nombre = nombreField.getText().trim();
            String precio = precioField.getText().trim();
            String cantidad = cantidadField.getText().trim();

            if (!id.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty()) {
                agregarProducto(id, nombre, precio, cantidad);
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                          


