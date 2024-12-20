#include <iostream>
#include <sstream>
#include <string>
#include <cstring>
#include <cstdlib>
#include <unistd.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>

const int PORT = 8080;  // Port number

// Function to handle the HTTP response
void handle_request(int client_sock) {
    // Read the HTTP request (but we don't need to parse it for this example)
    char buffer[1024];
    int bytes_read = read(client_sock, buffer, sizeof(buffer) - 1);
    if (bytes_read < 0) {
        std::cerr << "Error reading request" << std::endl;
        return;
    }

    buffer[bytes_read] = '\0';  // Null-terminate the request

    // Print the request for debugging purposes
    std::cout << "Received request:\n" << buffer << std::endl;

    // Simple HTTP response
    std::string http_response =
        "HTTP/1.1 200 OK\r\n"
        "Content-Type: text/html; charset=UTF-8\r\n"
        "Connection: close\r\n"
        "\r\n"
        "<!DOCTYPE html>\r\n"
        "<html>\r\n"
        "<head><title>Simple HTTP Server</title></head>\r\n"
        "<body><h1>Welcome to the Simple HTTP Server</h1></body>\r\n"
        "</html>\r\n";

    // Send the response
    write(client_sock, http_response.c_str(), http_response.size());
}

// Function to start the server
void start_server() {
    // Create a socket
    int server_sock = socket(AF_INET, SOCK_STREAM, 0);
    if (server_sock < 0) {
        std::cerr << "Error opening socket" << std::endl;
        exit(1);
    }

    // Set up server address
    sockaddr_in server_addr;
    std::memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;  // Bind to all available interfaces
    server_addr.sin_port = htons(PORT);

    // Bind the socket to the address
    if (bind(server_sock, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
        std::cerr << "Error binding socket" << std::endl;
        close(server_sock);
        exit(1);
    }

    // Listen for incoming connections
    if (listen(server_sock, 5) < 0) {
        std::cerr << "Error listening on socket" << std::endl;
        close(server_sock);
        exit(1);
    }

    std::cout << "Server started on port " << PORT << "...\n";

    while (true) {
        // Accept incoming client connections
        sockaddr_in client_addr;
        socklen_t client_len = sizeof(client_addr);
        int client_sock = accept(server_sock, (struct sockaddr*)&client_addr, &client_len);
        if (client_sock < 0) {
            std::cerr << "Error accepting connection" << std::endl;
            continue;
        }

        // Handle the client request
        handle_request(client_sock);

        // Close the client socket
        close(client_sock);
    }

    // Close the server socket
    close(server_sock);
}

int main() {
    start_server();
    return 0;
}
